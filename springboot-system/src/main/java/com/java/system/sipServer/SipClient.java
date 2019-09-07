package com.java.system.sipServer;

import com.java.common.utils.StringUtil;
import com.java.common.utils.UNIDGenerate;
import com.java.model.domain.DeviceConfig;
import com.java.model.domain.StreamMediaConfig;
import com.java.system.UDPService;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SipClient {

    private DatagramSocket sipClient;

    private String callId1 = "";
    private String callId2 = "";
    private String localIp = ""; // 本地ip
    private Integer localPort; // 本地端口

    private final String streamTag = StringUtil.getRandomStringByLength(8);
    private final String deviceTag = StringUtil.getRandomStringByLength(8);

    private final String streamBranch = StringUtil.getRandomStringByLength(8);
    private final String deviceBranch = StringUtil.getRandomStringByLength(8);

    private DeviceConfig dc = null;
    private StreamMediaConfig smc = null;

    private final String sipServiceNum = "34020000002000000001"; // SIP服务编号

    public void startClient(DeviceConfig deviceConfig, StreamMediaConfig streamMediaConfig, Integer port){
        dc = deviceConfig;
        smc = streamMediaConfig;
        localPort = port;
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
            localIp = address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // 向流媒体发送INVITE请求
        String result = send_stream_invite();
        // 向设备发送INVITE请求
        String result1 = send_device_invite(result);
        // 向流媒体发送ACK请求
        send_stream_ack(result1);
        // 向社保发送ACK请求
        send_device_ack();
    }

    public String send_stream_invite(){
        callId1 = new UNIDGenerate().getUnid();
        // 发送数据
        String msg = "INVITE sip:" + smc.getSipServerNumber() + "@" + smc.getSipServerIp()+ ":" + smc.getSipPort() + " SIP/2.0\n" +
                "Call-ID: " + callId1 + "@" + localIp + "\n" +
                "CSeq: 1 INVITE\n" +
                "From: <sip:" + sipServiceNum + "@" + localIp + ":" + localPort + ">;tag=" + streamTag + "\n" +
                "To: <sip:" + smc.getSipServerNumber() + "@" + smc.getSipServerIp()+ ":" + smc.getSipPort() + ">\n" +
                "Via: SIP/2.0/UDP " + localIp + ":" + localPort + ";branch=" + streamBranch + "\n" +
                "Max-Forwards: 70\n" +
                "Contact: <sip:" + sipServiceNum + "@" + localIp + ":" + localPort + ">\n" +
                "Subject: " + dc.getDeviceNumber() + ":" + dc.getId() + "," + smc.getSipServerNumber() + ":" + smc.getId() + "\n\n";
        //打印响应的数据
        String SERVICE_IP = smc.getSipServerIp();

        int SERVICE_PORT = smc.getSipPort();
        String result = sendAndReceive(SERVICE_IP, SERVICE_PORT, msg,true);
        System.out.println(result);

        return result;
    }

    public String send_device_invite(String res){
        String[] ress = res.split("\n\n");

        callId2 = new UNIDGenerate().getUnid();
        // 发送数据
        String msg = "INVITE sip:" + dc.getDeviceNumber() + "@" + dc.getVisitAddress() + ":" + dc.getVisitPort() + " SIP/2.0\n" +
                "Call-ID: " + callId2 + "@" + localIp + "\n" +
                "CSeq: 1 INVITE\n" +
                "From: <sip:" + sipServiceNum + "@" + localIp + ":" + localPort + ">;tag=" + deviceTag + "\n" +
                "To: <sip:" + dc.getDeviceNumber() + "@" + dc.getVisitAddress() + ":" + dc.getVisitPort() + ">\n" +
                "Via: SIP/2.0/UDP " + localIp + ":" + localPort + ";branch=" + deviceBranch + "\n" +
                "Max-Forwards: 70\n" +
                "Content-Type: APPLICATION/SDP\n"+
                "Contact: <sip:" + sipServiceNum + "@" + localIp + ":" + localPort + ">\n" +
                "Subject: " + dc.getDeviceNumber() + ":" + dc.getId() + "," + smc.getSipServerNumber() + ":" + smc.getId() + "\n\n"+
                ress[1]+
                "y=" + dc.getDeviceNumber().substring((dc.getDeviceNumber().length()-3), dc.getDeviceNumber().length()) + "\n";
        //打印响应的数据
        String SERVICE_IP = dc.getVisitAddress();

        int SERVICE_PORT = dc.getVisitPort();
        String result = sendAndReceive(SERVICE_IP, SERVICE_PORT, msg,true);
        System.out.println(result);

        return result;
    }

    public String send_stream_ack(String res){
        String[] ress = res.split("\r\n\r\n");

        // 发送数据
        String msg = "ACK sip:" + smc.getSipServerNumber() + "@" + smc.getSipServerIp()+ ":" + smc.getSipPort() + " SIP/2.0\n" +
                "Call-ID: " + callId1 + "@" + localIp + "\n" +
                "CSeq: 1 ACK\n" +
                "From: <sip:" + sipServiceNum + "@" + localIp + ":" + localPort + ">;tag=" + streamTag + "\n" +
                "To: <sip:" + smc.getSipServerNumber() + "@" + smc.getSipServerIp()+ ":" + smc.getSipPort() + ">\n" +
                "Via: SIP/2.0/UDP " + localIp + ":" + localPort + ";branch=" + streamBranch + "\n" +
                "Max-Forwards: 70\n" +
                "Content-Type: APPLICATION/SDP\n\n"+
                ress[1];
        //打印响应的数据
        String SERVICE_IP = smc.getSipServerIp();

        int SERVICE_PORT = smc.getSipPort();
        String result = sendAndReceive(SERVICE_IP, SERVICE_PORT, msg, false);
        System.out.println(result);

        return result;
    }

    public String send_device_ack(){

        // 发送数据
        String msg = "ACK sip:" + dc.getDeviceNumber() + "@" + dc.getVisitAddress() + ":" + dc.getVisitPort() + " SIP/2.0\n" +
                "Call-ID: " + callId2 + "@" + localIp + "\n" +
                "CSeq: 1 ACK\n" +
                "From: <sip:" + sipServiceNum + "@" + localIp + ":" + localPort + ">;tag=" + deviceTag + "\n" +
                "To: <sip:" + dc.getDeviceNumber() + "@" + dc.getVisitAddress() + ":" + dc.getVisitPort() + ">\n" +
                "Via: SIP/2.0/UDP " + localIp + ":" + localPort + ";branch=" + deviceBranch + "\n" +
                "Max-Forwards: 70\n";
        //打印响应的数据
        String SERVICE_IP = dc.getVisitAddress();

        int SERVICE_PORT = dc.getVisitPort();
        String result = sendAndReceive(SERVICE_IP, SERVICE_PORT, msg,false);
        System.out.println(result);

        return result;
    }

    private String sendAndReceive(String ip, int port, String msg, boolean flag) {
        String responseMsg = "";

        try {
            //创建客户端的DatagramSocket对象，不必传入地址和对象
            sipClient = SipService.sipService;
            System.out.println("请求数据\n"+msg);
            byte[] sendBytes = msg.getBytes();
            //封装要发送目标的地址
            InetAddress address = InetAddress.getByName(ip);
            //封装要发送的DatagramPacket的对象，由于要发送到目的主机，所以要加上地址和端口号
            DatagramPacket sendPacket = new DatagramPacket(sendBytes, sendBytes.length, address, port);

            try {
                //发送数据
                sipClient.send(sendPacket);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(flag) {
                byte[] responseBytes = new byte[UDPService.MAX_BYTES];
                //创建响应信息的DatagramPacket对象
                DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length);
                while (true) {
                    try {
                        //等待响应信息，同服务端一样，客户端也会在这一步阻塞，直到收到一个数据包
                        sipClient.receive(responsePacket);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //解析数据包内容
                    responseMsg = new String(responsePacket.getData(), 0, responsePacket.getLength());
                    if (responseMsg.startsWith("SIP/2.0 200 OK")) {
                        break;
                    }
//                System.out.println(responseMsg);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭客户端
//            if(sipClient != null){
//                sipClient.close();
//                sipClient = null;
//            }
        }

        return responseMsg;
    }

//    private String sendAndReceive1(String ip, int port, String msg, boolean flag) {
//        String responseMsg = "";
//
//        try {
//            //创建客户端的DatagramSocket对象，不必传入地址和对象
//            InetAddress address1 = InetAddress.getByName(localIp);
//            sipClient = SipService.sipService;
//            System.out.println("请求数据\n"+msg);
//            byte[] sendBytes = msg.getBytes();
//            //封装要发送目标的地址
//            InetAddress address = InetAddress.getByName(ip);
//            //封装要发送的DatagramPacket的对象，由于要发送到目的主机，所以要加上地址和端口号
//            DatagramPacket sendPacket = new DatagramPacket(sendBytes, sendBytes.length, address, port);
//
//            try {
//                //发送数据
//                sipClient.send(sendPacket);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//            if(flag) {
//                byte[] responseBytes = new byte[UDPService.MAX_BYTES];
//                //创建响应信息的DatagramPacket对象
//                DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length);
//                while (true) {
//                    try {
//                        //等待响应信息，同服务端一样，客户端也会在这一步阻塞，直到收到一个数据包
//                        sipClient.receive(responsePacket);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    //解析数据包内容
//                    responseMsg = new String(responsePacket.getData(),0, responsePacket.getLength());
//                    if (responseMsg.startsWith("SIP/2.0 200 OK")) {
//                        break;
//                    }
////                System.out.println(responseMsg);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            //关闭客户端
////            if(sipClient != null){
////                sipClient.close();
////                sipClient = null;
////            }
//        }
//
//        return responseMsg;
//    }
}
