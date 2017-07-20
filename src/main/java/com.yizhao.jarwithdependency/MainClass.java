package com.yizhao.jarwithdependency;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * mvn clean package
 * java -jar /Users/yzhao/IdeaProjects/jarwithdependency/target/jarwithdependency-jar-with-dependencies.jar
 */
public class MainClass {
    public static void main(String[] args){
        MainClass sendEmail = new MainClass();
        sendEmail.send();
    }

    private final String from = "pde.alarm@gmail.com";

    // Assuming you are sending email from localhost
    private final String host = "smtp.gmail.com";

    // Username
    private final String user = "pde.alarm@gmail.com";
    private final String password = "pdealarm123";

    public void send(){
        // Get system properties
        Properties properties = System.getProperties();

        properties.setProperty("mail.host", host);
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        Authenticator auth = new SMTPAuthenticator(user, password);

        // Get the default Session object.
        Session session = Session.getInstance(properties, auth);

        // Set response content type
        MimeMessage message = null;
        try {
            // Create a default MimeMessage object.
            message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            Address[]  recipients = {new InternetAddress("yizhao.cs@gmail.com")};
            message.addRecipients(Message.RecipientType.TO, recipients);

            // Set Subject: header field
            message.setSubject("Send Email Testing from Yi Zhao");
            // Now set the actual message
            message.setText("Hello world!");
            // Send message
            Transport.send(message);
        } catch (AddressException e) {
        } catch (javax.mail.MessagingException e) {
        }
    }


    private class SMTPAuthenticator extends Authenticator {
        private PasswordAuthentication authentication;

        public SMTPAuthenticator(String login, String password) {
            authentication = new PasswordAuthentication(login, password);
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return authentication;
        }
    }
}
