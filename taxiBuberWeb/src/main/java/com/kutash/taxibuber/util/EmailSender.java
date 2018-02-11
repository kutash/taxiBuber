package com.kutash.taxibuber.util;

import com.kutash.taxibuber.entity.User;
import com.kutash.taxibuber.resource.EmailManager;
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
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * The type Email sender.
 */
public class EmailSender {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Send new password.
     *
     * @param user     the user
     * @param language the language
     */
    public void sendNewPassword(User user,String language){
        final Properties properties = EmailManager.getInstance().getProperties();
        final String sender = properties.getProperty("mail.user.name");
        final String password = properties.getProperty("mail.user.password");

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(sender, password);
            }
        });
        String theme = "Password";
        STGroup group = new STGroupFile("emailTemplates.stg");
        ST st;
        if (language.equals("ru") || language.equals("RU_ru")){
            st = group.getInstanceOf("template1");
        }else {
            st = group.getInstanceOf("template2");
        }
        String newPassword = null;
        try {
            newPassword = new String(user.getPassword(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.catching(Level.ERROR,e);
        }
        st.add("user", user);
        st.add("password",newPassword);
        sendEmail(user.getEmail(), theme, st.render(), properties, session);
    }

    private void sendEmail(String address, String theme, String text, Properties properties, Session session){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty("mail.user.name"), properties.getProperty("ADMIN-NAME")));
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
            message.setSubject(theme);
            message.setText(text);
            Transport.send(message);
        } catch (Exception e) {
            LOGGER.catching(Level.ERROR,e);
        }
    }
}
