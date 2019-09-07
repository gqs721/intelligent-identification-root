package com.java.system;

import com.java.common.utils.DateUtil;
import com.java.common.utils.MD5Util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class UDPService {

    /**
     * 保存正在注册的用户，注册第一步的
     */
    private static Set<String> registingId = new HashSet<>();

    /**
     * 保存当前注册的用户，注册成功的
     */
    private static Hashtable<String, String> registedContactURI = new Hashtable<>();

    public static final String SERVICE_IP = "192.168.1.156";

    public static final int SERVICE_PORT = 8088;

    public static final int MAX_BYTES = 2048;

    private DatagramSocket service;

    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        System.out.println(address.getHostAddress());
//        System.out.println("34020000001180000001".substring(10,13));
        UDPService udpService = new UDPService();
        udpService.startService(SERVICE_IP,SERVICE_PORT);//启动服务端
    }

    private void startService(String ip, int port) {
        try {
            //包装IP地址
            InetAddress address = InetAddress.getByName(ip);
            //创建服务端的DatagramSocket对象，需要传入地址和端口号
            service = new DatagramSocket(port,address);

            byte[] receiveBytes = new byte[MAX_BYTES];
            //创建接受信息的包对象
            DatagramPacket receivePacket = new DatagramPacket(receiveBytes,receiveBytes.length);

            //开启一个死循环，不断接受数据
            while(true){
                try{
                    //接收数据，程序会阻塞到这一步，直到收到一个数据包为止
                    service.receive(receivePacket);
                }catch (Exception e){
                    e.printStackTrace();
                }

                //解析收到的数据
                String receiveMsg = new String(receivePacket.getData(),0,receivePacket.getLength());

                System.out.println("请求数据 request\n"+receiveMsg);
                Map<String, String> map = new HashMap();
                String response = "";
                if(receiveMsg.startsWith("REGISTER")){

                    dealMsg(receiveMsg, map);

                    String toUserId = map.get("userName");
                    if(registedContactURI.containsKey(toUserId)) {//已经注册了
                        System.out.println("已经注册过了 user=" + toUserId);
                    }else {//不是注册成功状态
                        if (registingId.contains(toUserId)) {//是第二次注册
                            boolean authorizationResult = false;
                            String Authorization = map.get("Authorization");
                            String[] str = Authorization.split(", ");
                            Map<String, String> map1 = new HashMap<>();
                            for (int i = 0; i < str.length; i++) {
                                map1.put(str[i].split("=")[0], str[i].split("=")[1]);
                            }
                            String username = map1.get("Digest username").replaceAll("\"", "");
                            String realm = map1.get("realm").replaceAll("\"", "");
                            String nonce = map1.get("nonce").replaceAll("\"", "");
                            String uri = map1.get("uri").replaceAll("\"", "");
                            String res = map1.get("response").replaceAll("\"", "");
                            String algorithm = map1.get("algorithm");
                            if (null == username || null == realm || null == nonce || null == uri || null == res || null == algorithm) {
                                System.out.println("Authorization信息不全，无法认证。");
                            } else {
                                // 比较Authorization信息正确性
                                String A1 = MD5Util.MD5Encode(username + ":" + realm + ":123456");
                                String A2 = MD5Util.MD5Encode("REGISTER:" + uri.toString());
                                String resStr = MD5Util.MD5Encode(A1 + ":" + nonce + ":" + A2);
                                System.out.println("response=" + resStr);
                                if (resStr.equals(res)) {
                                    //注册成功，标记true
                                    authorizationResult = true;
                                }
                            }
                            registingId.remove(toUserId);//不管第二次是否成功都移除，失败要从头再来
                            // 验证成功加入成功注册列表，失败不加入
                            if (authorizationResult) {//注册成功
                                System.out.println("注册成功！");
                                registedContactURI.put(toUserId, map.get("Contact"));
                                //返回成功
                                response = "SIP/2.0 200 OK\n" +
                                        "To: " + map.get("To") + "\n" +
                                        "Contact: " + map.get("Contact") + "\n" +
                                        "Content-Length: 0\n" +
                                        "CSeq: 2 REGISTER\n" +
                                        "Call-ID: " + map.get("Call-ID") + "\n" +
                                        "From: " + map.get("From") + "\n" +
                                        "Via: " + map.get("Via") + "\n" +
                                        "Date: " + DateUtil.getCurrentDate() + "\n" +
                                        "Expires: 3600\n";

//                            System.out.println("返回注册结果 response是\n" + response.toString());

                                sendMsg(receivePacket, response);

                                // 发送流媒体服务器请求
//                            sendRequest("34020000002000000001", "192.168.1.156", 8080);
                            } else {//注册失败
                                System.out.println("注册失败！\n\n");
                                //返回失败
                                response = "";
//                            System.out.println("返回注册结果 response是\n" + response.toString());

//                            sendMsg(receivePacket, response);
                            }
                        } else {//是第一次注册
                            System.out.println("首次注册 user=" + toUserId);
                            registingId.add(toUserId);
                            String realm = "zectec";
                            String nonce = MD5Util.MD5Encode(map.get("Call-ID") + toUserId);

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
                    }
                } else if (receiveMsg.startsWith("INVITE")) {

                } else if (receiveMsg.startsWith("MESSAGE")) {
                    System.out.println("不处理的requestMethod：MESSAGE");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭DatagramSocket对象
            if(service!=null){
                service.close();
                service = null;
            }
        }
    }

    public void sendMsg(DatagramPacket receivePacket, String response){
        //解析客户端地址
        InetAddress clientAddress = receivePacket.getAddress();
        //解析客户端端口
        int clientPort = receivePacket.getPort();

        //组建响应信息
//                String response = "SIP/2.0 401 Unauthorized\n" +
//                        "To: <sip:34020000001320000101@192.168.1.101:5060>\n" +
//                        "Content-Length: 0\n" +
//                        "CSeq: 1 REGISTER\n" +
//                        "Call-ID: "+map.get("Call-ID")+"\n" +
//                        "From: <sip:34020000001320000101@192.168.1.101:5060>;tag=199156044\n" +
//                        "Via: SIP/2.0/UDP 192.168.1.101:5060;rport=5060;branch=z9hG4bK383183186;received=192.168.1.101\n" +
//                        "WWW-Authenticate: Digest realm=\"zectec\",nonce=\"a5c5d750bb947abc7c50d98e5b3d7bb6\"\n\n";
        System.out.println("返回结果 response是\n" + response);
        byte[] responseBuf = response.getBytes();
        //创建响应信息的包对象，由于要发送到目的地址，所以要加上目的主机的地址和端口号
        DatagramPacket responsePacket = new DatagramPacket(responseBuf,responseBuf.length,clientAddress,clientPort);

        try{
            //发送数据
            service.send(responsePacket);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void dealMsg(String receiveMsg, Map<String, String> map){
        String[] str = receiveMsg.split("\r\n");
//        Map<String, String> map = new HashMap();
        for (int i = 0; i < str.length; i++){
            if(str[i].startsWith("REGISTER")|| str[i] == "") {

            }else
                map.put(str[i].split(": ")[0], str[i].split(": ")[1]);
        }
        String to = map.get("To");
        String username = to.substring(to.indexOf(":") + 1, to.indexOf("@"));
        map.put("userName", username);

//        String via = map.get("Via");
//        String branch = via.split(";")[2].split("=")[1];
//        map.put("branch", branch);
//
//        String from = map.get("From");
//        String tag = from.split(";")[1].split("=")[1];
//        map.put("tag", tag);


//        return null;
    }

    public static void main1(String[] args) {
        String msg = "REGISTER sip:34020000002020000001@192.168.1.156:5071;transport=udp SIP/2.0\n"+
                "Content-Length: 0\n"+
                "From: <sip:34020000001320000101@192.168.1.101:5060>;tag=1129943503\n" +
                "To: <sip:34020000001320000101@192.168.1.101:5060>\n" +
                "CSeq: 1 REGISTER\n" +
                "Call-ID: %d\n" +
                "Contact: <sip:34020000001320000101@192.168.1.101:5060>\n" +
                "Via: SIP/2.0/UDP 192.168.1.101:5060;rport=5060;branch=z9hG4bK842158774;received=192.168.1.101\n" +
                "Expires: 3600\n" +
                "Max-Forwards: 70\n\n";
//        dealMsg(msg);
    }

}