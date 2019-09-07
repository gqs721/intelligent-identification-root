package com.java.system;

import com.java.common.utils.UNIDGenerate;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    private static DatagramSocket client;

    private static String callId1= "";
    private static String callId2= "";

    public static void main(String[] args) {
        UDPClient client = new UDPClient();
        client.startClient();
    }

    public void startClient(){
        // 向流媒体发送第一次INVITE请求
        String result = send_stream_invite();
        // 向设备发送第一次INVITE请求
        String result1 = send_device_invite(result);

        send_stream_ack(result1);

        send_device_ack();
    }

    public String send_stream_invite(){
        callId1 = new UNIDGenerate().getUnid();
        // 发送数据
        String msg = "INVITE sip:34020000002020000001@192.168.1.80:5071 SIP/2.0\n" +
                "Call-ID: " + callId1 + "@192.168.1.156\n" +
                "CSeq: 1 INVITE\n" +
                "From: <sip:34020000002000000001@192.168.1.156:8088>;tag=textclientv1.0\n" +
                "To: <sip:34020000002020000001@192.168.1.80:5071>\n" +
                "Via: SIP/2.0/UDP 192.168.1.156:8088;branch=branch1\n" +
                "Max-Forwards: 70\n" +
                "Contact: <sip:34020000002000000001@192.168.1.156:8088>\n" +
                "Subject: 34020000001320000101:20000101,34020000002020000001:20000001\n\n";
        //打印响应的数据
        String SERVICE_IP = "192.168.1.80";

        int SERVICE_PORT = 5071;
        String result =sendAndReceive(SERVICE_IP,SERVICE_PORT,msg);
        System.out.println(result);

        return result;
    }

    public String send_device_invite(String res){
        String[] ress = res.split("\n\n");

        callId2 = new UNIDGenerate().getUnid();
        // 发送数据
        String msg = "INVITE sip:34020000001320000101@192.168.1.101:5060 SIP/2.0\n" +
                "Call-ID: " + callId2 + "@192.168.1.156\n" +
                "CSeq: 1 INVITE\n" +
                "From: <sip:34020000002000000001@192.168.1.156:8088>;tag=textclientv2.0\n" +
                "To: <sip:34020000001320000101@192.168.1.101:5060>\n" +
                "Via: SIP/2.0/UDP 192.168.1.156:8088;branch=branch2\n" +
                "Max-Forwards: 70\n" +
                "Content-Type: APPLICATION/SDP\n"+
                "Contact: <sip:34020000002000000001@192.168.1.156:8088>\n" +
                "Subject: 34020000001320000101:20000101,34020000002020000001:20000001\n\n"+
                ress[1]+
                "y=34020000001320000101\n";
        //打印响应的数据
        String SERVICE_IP = "192.168.1.101";

        int SERVICE_PORT = 5060;
        String result =sendAndReceive1(SERVICE_IP,SERVICE_PORT,msg, true);
        System.out.println(result);

        return result;
    }

    public String send_stream_ack(String res){
        String[] ress = res.split("\r\n\r\n");

        UDPClient client = new UDPClient();
        // 发送数据
        String msg = "ACK sip:34020000002020000001@192.168.1.80:5071 SIP/2.0\n" +
                "Call-ID: " + callId1 + "@192.168.1.156\n" +
                "CSeq: 1 ACK\n" +
                "From: <sip:34020000002000000001@192.168.1.156:8088>;tag=textclientv1.0\n" +
                "To: <sip:34020000002020000001@192.168.1.80:5071>\n" +
                "Via: SIP/2.0/UDP 192.168.1.156:8088;branch=branch2\n" +
                "Max-Forwards: 70\n" +
                "Content-Type: APPLICATION/SDP\n\n"+
                ress[1];
        //打印响应的数据
        String SERVICE_IP = "192.168.1.80";

        int SERVICE_PORT = 5071;
        String result =sendAndReceive1(SERVICE_IP,SERVICE_PORT,msg, false);
        System.out.println(result);

        return result;
    }

    public String send_device_ack(){
//        String[] ress = res.split("\n\n");

        UDPClient client = new UDPClient();
        // 发送数据
        String msg = "ACK sip:34020000001320000101@192.168.1.101:5060 SIP/2.0\n" +
                "Call-ID: " + callId2 + "@192.168.1.156\n" +
                "CSeq: 1 ACK\n" +
                "From: <sip:34020000002000000001@192.168.1.156:8088>;tag=textclientv2.0\n" +
                "To: <sip:34020000001320000101@192.168.1.101:5060>\n" +
                "Via: SIP/2.0/UDP 192.168.1.156:8088;branch=branch2\n" +
                "Max-Forwards: 70\n";
//                "Content-Type: APPLICATION/SDP\n\n";
//                ress[1];
        //打印响应的数据
        String SERVICE_IP = "192.168.1.101";

        int SERVICE_PORT = 5060;
        String result =sendAndReceive1(SERVICE_IP,SERVICE_PORT,msg, false);
        System.out.println(result);

        return result;
    }

    private String sendAndReceive(String ip, int port, String msg) {
        String responseMsg = "";

        try {
            //创建客户端的DatagramSocket对象，不必传入地址和对象
            InetAddress address1 = InetAddress.getByName("192.168.1.156");
            client = new DatagramSocket(8088, address1);
            System.out.println("请求数据\n"+msg);
            byte[] sendBytes = msg.getBytes();
            //封装要发送目标的地址
            InetAddress address = InetAddress.getByName(ip);
            //封装要发送的DatagramPacket的对象，由于要发送到目的主机，所以要加上地址和端口号
            DatagramPacket sendPacket = new DatagramPacket(sendBytes,sendBytes.length,address,port);

            try {
                //发送数据
                client.send(sendPacket);
            }catch (Exception e){
                e.printStackTrace();
            }

            byte[] responseBytes = new byte[UDPService.MAX_BYTES];
            //创建响应信息的DatagramPacket对象
            DatagramPacket responsePacket = new DatagramPacket(responseBytes,responseBytes.length);
            try {
                //等待响应信息，同服务端一样，客户端也会在这一步阻塞，直到收到一个数据包
                client.receive(responsePacket);
            }catch (Exception e){
                e.printStackTrace();
            }

            //解析数据包内容
            responseMsg = new String(responsePacket.getData(),0,responsePacket.getLength());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭客户端
            if(client != null){
                client.close();
                client = null;
            }
        }

        return responseMsg;
    }

    private String sendAndReceive1(String ip, int port, String msg, boolean flag) {
        String responseMsg = "";

        try {
            //创建客户端的DatagramSocket对象，不必传入地址和对象
            InetAddress address1 = InetAddress.getByName("192.168.1.156");
            client = new DatagramSocket(8088, address1);
            System.out.println("请求数据\n"+msg);
            byte[] sendBytes = msg.getBytes();
            //封装要发送目标的地址
            InetAddress address = InetAddress.getByName(ip);
            //封装要发送的DatagramPacket的对象，由于要发送到目的主机，所以要加上地址和端口号
            DatagramPacket sendPacket = new DatagramPacket(sendBytes,sendBytes.length,address,port);

            try {
                //发送数据
                client.send(sendPacket);
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
                        client.receive(responsePacket);
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
            if(client != null){
                client.close();
                client = null;
            }
        }

        return responseMsg;
    }
}