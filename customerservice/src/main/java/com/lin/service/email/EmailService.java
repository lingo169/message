package com.lin.service.email;

import com.lin.service.customer.CustomerGroupServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 邮箱发送服务
 */
@Component
public class EmailService {


    @Value("${cs.email.authorizationCode}")
    private String authorizationCode;

    Logger log = LoggerFactory.getLogger(CustomerGroupServiceImpl.class);

    /*public static void main(String[] args) {
        // Recipient's email ID needs to be mentioned.
        //String to = "linger169@aliyun.com";
        // Sender's email ID needs to be mentioned
        //String from = "508430313@qq.com";
        String to = "linger169@aliyun.com";
        String from = "508430313@qq.com";
        sendEmail(from,to);
    }*/

    public boolean sendEmail(String from,String to,String htmlbody) {
        // Assuming you are sending email from through 163邮箱 smtp，
        // QQ邮箱 is smtp.qq.com ,port is 465/587
        String host = "smtp.qq.com";
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        // 646524284@qq的授权号：fqxedcjwhjbcbehf
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, authorizationCode);
            }
        });
        // Used to debug SMTP issues
        //session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            //set message headers
            message.addHeader("Content-type", "text/HTML;charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: header field
            message.setSubject("This is the Subject Line!");
            // Now set the actual message
            // message.setText("This is actual message");
            message.setContent(htmlbody,"text/html;charset=UTF-8");
            log.info("sending...");
            // Send message
            Transport.send(message);
            log.info("Sent message successfully....");
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            log.error("send email exception: {}",mex);
        }
        return false;
    }
}
