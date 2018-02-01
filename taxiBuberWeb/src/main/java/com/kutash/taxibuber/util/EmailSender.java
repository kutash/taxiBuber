package com.kutash.taxibuber.util;

import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.service.LoginService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class EmailSender {

    private static final Logger LOGGER = LogManager.getLogger();

    public void sendNewPassword(User user, String language){
        final Properties properties = new Properties();
        String theme = "Password";
        try {
            properties.load(LoginService.class.getResourceAsStream("/email.properties"));
            final String sender = properties.getProperty("mail.user.name");
            final String password = properties.getProperty("mail.user.password");

            Session session = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                            return new javax.mail.PasswordAuthentication(sender, password);
                        }
                    });

            STGroup group = new STGroupFile("emailTemplates.stg");
            ST st = new ST();
            if (language.equals("ru") || language.equals("RU_ru")){
                st = group.getInstanceOf("template1");
            }else {
                st = group.getInstanceOf("template2");
            }
            st.add("user", user);
            new EmailSender().sendEmail(user.getEmail(), theme, st.render(), properties, sender, session);
        } catch (IOException e) {
            LOGGER.catching(Level.ERROR,e);
        }
    }

    private void sendEmail(String address, String theme, String text, Properties properties, String sender, Session session){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender, properties.getProperty("ADMIN-NAME")));
            message.addRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(address));
            message.setSubject(theme);
            message.setText(text);
            Transport.send(message);
        } catch (Exception e) {
            LOGGER.catching(Level.ERROR,e);
        }
    }
}
