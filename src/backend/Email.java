package backend;

// File Name SendHTMLEmail.java

import java.util.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Email {
    public static void email(){
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        final String username = "edward.nardo@gmail.com";//
        final String password = "US.marines.2010";
        try{
            Session session = Session.getDefaultInstance(props,
                    new Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }});

            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress("edward.nardo@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("enardo@ycp.edu",false));
            msg.setSubject("Hello");
            msg.setContent("<h1>Thank you for registering</h1><br><h3>Lets make something together</h3>", "text/html");
            msg.setSentDate(new Date());
            Transport.send(msg);
            System.out.println("Message sent.");
        }catch (MessagingException e){ System.out.println("error " + e);}
    }

/*public class Email {
    public static void email(){
        // Recipient's email ID needs to be mentioned.
        String to = "enardo@ycp.edu";

        // Sender's email ID needs to be mentioned
        String from = "enard@ycp.edu";

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        properties.setProperty("mail.user", "enardo");

        properties.setProperty("mail.password", "US.marines.2010");

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Send the actual HTML message, as big as you like
            message.setContent("<h1>This is actual message</h1>", "text/html");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }*/

}
