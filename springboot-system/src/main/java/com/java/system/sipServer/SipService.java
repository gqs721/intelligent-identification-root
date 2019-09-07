package com.java.system.sipServer;

import com.java.common.utils.DateUtil;
import com.java.common.utils.MD5Util;
import com.java.common.utils.StringUtil;
import com.java.common.utils.VerifyCodeUtils;
import com.java.model.dao.DeviceConfigMapper;
import com.java.model.dao.NvrConfigMapper;
import com.java.model.dao.StreamMediaConfigMapper;
import com.java.model.domain.DeviceConfig;
import com.java.model.domain.NvrConfig;
import com.java.model.domain.StreamMediaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class SipService {

    // 拉流和图片处理的线程池
    final ExecutorService executorFixed = Executors.newCachedThreadPool();

    @Value("${server.port}")
    private Integer port;

    @Autowired
    private DeviceConfigMapper deviceConfigMapper;

    @Autowired
    private NvrConfigMapper nvrConfigMapper;

    @Autowired
    private StreamMediaConfigMapper streamMediaConfigMapper;

    /**
     * 保存正在注册的用户，注册第一步的
     */
    private static Set<String> registingId = new HashSet<>();

    /**
     * 保存正在注销的用户，注销第一步的
     */
    private static Set<String> cancelledId = new HashSet<>();

    /**
     * 保存当前注册的用户，注册成功的
     */
    private static Hashtable<String, String> registedContactURI = new Hashtable<>();

    public static final int MAX_BYTES = 2048;

    public static DatagramSocket sipService;

    @PostConstruct
    private void startService() {
        executorFixed.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //包装IP地址
                    InetAddress address = InetAddress.getLocalHost();
                    //创建服务端的DatagramSocket对象，需要传入地址和端口号
                    sipService = new DatagramSocket(port, address);

                    byte[] receiveBytes = new byte[MAX_BYTES];

                    //开启一个死循环，不断接受数据
                    while(true){

                        //创建接受信息的包对象
                        DatagramPacket receivePacket = new DatagramPacket(receiveBytes, receiveBytes.length);
                        try{
                            //接收数据，程序会阻塞到这一步，直到收到一个数据包为止
                            sipService.receive(receivePacket);
                        }catch (Exception e){
                            e.printStackTrace();
                            continue;
                        }

                        //解析收到的数据
                        String receiveMsg = new String(receivePacket.getData(),0, receivePacket.getLength());
                        String response = "";

                        System.out.println("请求数据 request\n" + receiveMsg);

                        // 如果expires不等于0,则为注册，否则为注销。
                        if(receiveMsg.startsWith("REGISTER")){ // 注册、注销

                            Map<String, String> map = new HashMap();
                            dealMsg(receiveMsg, map);

                            int expires = Integer.parseInt(map.get("Expires"));

                            String deviveNumber = map.get("deviveNumber");

                            String password = "";
                            String userName = "";
                            String deviceType = "";
                            NvrConfig nc = null;
                            DeviceConfig dc = null;
                            // 判断是否是NVR设备
                            if (StringUtil.CheckIsEqual(deviveNumber.substring(10, 13), "118")) {
                                nc = nvrConfigMapper.findByNvrNumber(deviveNumber);
                                if (nc == null) {
                                    log.info("NVR设备没有在后台进行添加，无法注册。设备号：" + deviveNumber);
                                    continue;
                                } else if (nc.getStatus() != 0 || nc.getDelStatus() != 0) {
                                    log.info("NVR设备已被禁用或删除。设备号：" + deviveNumber);
                                    continue;
                                }
                                password = nc.getNvrPassword();
                                userName = nc.getNvrAccount();
                                deviceType = "nvr";
                            } else {
                                dc = deviceConfigMapper.findByDeviceNumber(deviveNumber);
                                if (dc == null) {
                                    log.info("设备没有在后台进行添加，无法注册。设备号：" + deviveNumber);
                                    continue;
                                } else if (dc.getStatus() != 0 || dc.getDelStatus() != 0) {
                                    log.info("设备已被禁用或删除。设备号：" + deviveNumber);
                                    continue;
                                }
                                password = dc.getDevicePassword();
                                userName = dc.getDeviceAccount();
                                deviceType = "common";
                            }

                            if (registingId.contains(deviveNumber)) {//是第二次注册
                                boolean authorizationResult = false;
                                String Authorization = map.get("Authorization");
                                String[] str = Authorization.split(", ");
                                Map<String, String> map1 = new HashMap<>();
                                for (int i = 0; i < str.length; i++) {
                                    map1.put(str[i].split("=")[0], str[i].split("=")[1]);
                                }

                                String realm = map1.get("realm").replaceAll("\"", "");
                                String nonce = map1.get("nonce").replaceAll("\"", "");
                                String uri = map1.get("uri").replaceAll("\"", "");
                                String res = map1.get("response").replaceAll("\"", "");
                                String algorithm = map1.get("algorithm");
                                if (null == realm || null == nonce || null == uri || null == res || null == algorithm) {
                                    System.out.println("Authorization信息不全，无法认证。");
                                } else {
                                    // 比较Authorization信息正确性
                                    String A1 = MD5Util.MD5Encode(userName + ":" + realm + ":" + password);
                                    String A2 = MD5Util.MD5Encode("REGISTER:" + uri);
                                    String resStr = MD5Util.MD5Encode(A1 + ":" + nonce + ":" + A2);
//                                System.out.println("response=" + resStr);
                                    if (resStr.equals(res)) {
                                        //注册成功，标记true
                                        authorizationResult = true;
                                    }
                                }
                                registingId.remove(deviveNumber);//不管第二次是否成功都移除，失败要从头再来
                                // 验证成功加入成功注册列表，失败不加入
                                if (authorizationResult) {//注册成功
                                    System.out.println("注册成功！设备号=" + deviveNumber);
                                    registedContactURI.put(deviveNumber, map.get("Contact"));
                                    //返回成功
                                    response = "SIP/2.0 200 OK\n" +
                                            "To: " + map.get("To") + "\n" +
                                            "Contact: " + map.get("Contact") + "\n" +
                                            "Content-Length: 0\n" +
                                            "CSeq: 2 REGISTER\n" +
                                            "Call-ID: " + map.get("Call-ID") + "\n" +
                                            "From: " + map.get("From") + "\n" +
                                            "Via: " + map.get("Via") + "\n" +
                                            "Date: " + DateUtil.getCurrentDate() + "\n";
//                                                "Expires: 3600\n";

                                    // 发送请求
                                    sendMsg(receivePacket, response);

                                    if(expires != 0) { // 注册成功后的操作
                                        if (StringUtil.CheckIsEqual(deviceType, "nvr")) {
                                            nc.setOnlineStatus("在线");
                                            nvrConfigMapper.updateByPrimaryKeySelective(nc);
                                        } else if (StringUtil.CheckIsEqual(deviceType, "common")) {
                                            dc.setOnlineStatus("在线");
                                            deviceConfigMapper.updateByPrimaryKeySelective(dc);
                                        }

                                        StreamMediaConfig smc = streamMediaConfigMapper.selectByPrimaryKey(dc.getStreamMediaId());

                                        // 发送流媒体服务器请求
                                        SipClient sipClient = new SipClient();
                                        sipClient.startClient(dc, smc, port);
                                    } else{ // 注销操作
                                        if (StringUtil.CheckIsEqual(deviceType, "nvr")) {
                                            nc.setOnlineStatus("离线");
                                            nvrConfigMapper.updateByPrimaryKeySelective(nc);
                                        } else if (StringUtil.CheckIsEqual(deviceType, "common")) {
                                            dc.setOnlineStatus("离线");
                                            deviceConfigMapper.updateByPrimaryKeySelective(dc);
                                        }
                                    }
                                } else {//注册失败
                                    System.out.println("注册失败！设备号=" + deviveNumber);
                                    //返回失败
                                    response = "";

                                    sendMsg(receivePacket, response);
                                }
                            } else {//是第一次注册
                                System.out.println("首次注册 deviceNumber=" + deviveNumber);
                                registingId.add(deviveNumber);
                                String realm = VerifyCodeUtils.generateNumberCode(8);
                                String nonce = MD5Util.MD5Encode(map.get("Call-ID") + deviveNumber);

                                response = "SIP/2.0 401 Unauthorized\n" +
                                        "To: " + map.get("To") + "\n" +
                                        "Content-Length: 0\n" +
                                        "CSeq: 1 REGISTER\n" +
                                        "Call-ID: " + map.get("Call-ID") + "\n" +
                                        "From: " + map.get("From") + "\n" +
                                        "Via: " + map.get("Via") + "\n" +
                                        "WWW-Authenticate: Digest realm=\"" + realm + "\",nonce=\"" + nonce + "\"\n\n";
                                sendMsg(receivePacket, response);
                            }
                        } else if (receiveMsg.startsWith("INVITE")) {

                        } else if(receiveMsg.startsWith("MESSAGE")){
                            Map<String, String> map = new HashMap();
                            dealMessage(receiveMsg, map);

                            response = "SIP/2.0 200 OK\n" +
                                    "To: " + map.get("To") + "\n" +
                                    "Contact: " + map.get("Contact") + "\n" +
                                    "Content-Length: 0\n" +
                                    "CSeq: 1 MESSAGE\n" +
                                    "Call-ID: " + map.get("Call-ID") + "\n" +
                                    "From: " + map.get("From") + "\n" +
                                    "Via: " + map.get("Via") + "\n" +
                                    "Date: " + DateUtil.getCurrentDate() + "\n";
                            // 发送请求
                            sendMsg(receivePacket, response);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //关闭DatagramSocket对象
                    if(sipService !=null){
                        sipService.close();
                        sipService = null;
                    }
                }
            }
        });

        System.out.println("SIP服务启动完成。。。");
    }

    public void sendMsg(DatagramPacket receivePacket, String response){
        //解析客户端地址
        InetAddress clientAddress = receivePacket.getAddress();
        //解析客户端端口
        int clientPort = receivePacket.getPort();

        byte[] responseBuf = response.getBytes();
        //创建响应信息的包对象，由于要发送到目的地址，所以要加上目的主机的地址和端口号
        DatagramPacket responsePacket = new DatagramPacket(responseBuf,responseBuf.length,clientAddress,clientPort);

        try{
            //发送数据
            sipService.send(responsePacket);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void dealMsg(String receiveMsg, Map<String, String> map){
        String[] str = receiveMsg.split("\r\n");
        for (int i = 0; i < str.length; i++){
            if(str[i].startsWith("REGISTER") || str[i].startsWith("MESSAGE")  || str[i] == "") {

            }else {
                map.put(str[i].split(": ")[0], str[i].split(": ")[1]);
            }
        }
        String to = map.get("To");
        String deviveNumber = to.substring(to.indexOf(":") + 1, to.indexOf("@"));
        map.put("deviveNumber", deviveNumber);
    }

    public static void dealMessage(String receiveMsg, Map<String, String> map){
        String[] str = receiveMsg.split("\r\n\r\n");
        dealMsg(str[0], map);
    }
}
