package com.java.common.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 邮件工具类
 */
public class MailUtil {
    /**
     * 发送邮件
     * @param to 给谁发
     * @param text 发送内容
     */
    public static void send_mail(String to,String text) throws MessagingException {
        //创建连接对象 连接到邮件服务器
        Properties properties = new Properties();
        //设置发送邮件的基本参数
        //发送邮件服务器
        properties.put("mail.smtp.host", "smtp.163.com");
        properties.put("mail.smtp.auth", "true");
        //设置发送邮件的账号和密码
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //两个参数分别是发送邮件的账户和密码
                return new PasswordAuthentication("bhzt2080@163.com","authTGBhu8");
            }
        });

        //创建邮件对象
        Message message = new MimeMessage(session);
        //设置发件人
        message.setFrom(new InternetAddress("bhzt2080@163.com"));
        //设置收件人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
        //设置主题
        message.setSubject("智能识别平台-邮箱找回密码");
        //设置邮件正文  第二个参数是邮件发送的类型
        text = "<p><br /></p><hr /><p>&nbsp;&nbsp;&nbsp;&nbsp;<strong>亲爱的用户： </strong><strong>您好！</strong></p><p>&nbsp;&nbsp;&nbsp;&nbsp;<strong>您在"+DateUtil.parseDateStr(DateUtil.getCurrentDate())+"提交了邮箱找回密码请求，<strong>请在验证码输入框中输入</strong>：</strong><strong><span style=\"color:#FF6600;font-size:24px;\">"+text+"</span></strong><strong>，以完成操作。</strong> </p><p>&nbsp;&nbsp;&nbsp;&nbsp;<strong>为了保证您的账号安全，该验证码有效期为30分钟。</strong> </p><p><br /></p><p>&nbsp;&nbsp;&nbsp;&nbsp;智能识别平台</p><p>&nbsp;&nbsp;&nbsp;&nbsp;"+DateUtil.parseDateYMDStr(DateUtil.getCurrentDate())+"</p><hr /><p><span style=\"color:#747474;font-family:arial, verdana, sans-serif;\">&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:#999999;\">此为系统邮件，请勿回复</span></span><span style=\"color:#999999;\"></span> </p><p>&nbsp; &nbsp; <span style=\"color:#999999;\">如果您没有进行过邮箱找回密码，则可能是其他用户的误操作，建议您立即登录平台查看，感谢您的支持！</span></p>";
        message.setContent(text,"text/html;charset=UTF-8");
        //发送一封邮件
        Transport.send(message);
    }
}
