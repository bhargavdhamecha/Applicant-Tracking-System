package com.email_service.Service;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

@ComponentScan
@Configuration
@PropertySource("classpath:application.properties")
@Service
public class EmailService {
    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
    private int connectionTimeout;

    @Value("${spring.mail.properties.mail.smtp.writetimeout}")
    private int writeTimeout;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;
   @Value("${spring.mail.properties.mail.smtp.ssl.enable}")
   private boolean ssl =true;

    public boolean sendEmail(String message, String subject, String to, File file) {

        boolean flag = false;
        Properties properties = System.getProperties();
        System.out.println("PROPERTIES "+properties);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.enable",ssl);
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.connectiontimeout", connectionTimeout);
        properties.put("mail.smtp.writetimeout", writeTimeout);


        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }

        });

        session.setDebug(true);


        MimeMessage mail = new MimeMessage(session);

        try {

            InternetAddress toInternetAddress = new InternetAddress(to);
            mail.setFrom(username);
            mail.addRecipient(Message.RecipientType.TO, toInternetAddress);

            mail.setSubject(subject);
       if(file == null) {
           mail.setText(message);
       }else {
           MimeBodyPart part1 = new MimeBodyPart();
           part1.setText(message);

           MimeBodyPart part2 = new MimeBodyPart();
           part2.attachFile(file);

           MimeMultipart multipart = new MimeMultipart();
           multipart.addBodyPart(part1);
           multipart.addBodyPart(part2);

           mail.setContent(multipart);
       }
            Transport.send(mail);

            System.out.println("Sent success...................");
            flag = true;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
