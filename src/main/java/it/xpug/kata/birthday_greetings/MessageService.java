package it.xpug.kata.birthday_greetings;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MessageService {
    private final String smtpHost;
    private final int smtpPort;
    private final String sender;

    public MessageService(String smtpHost, int smtpPort, String sender) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.sender = sender;
    }

    public void send(BirthdayMessage birthdayMessage){
        try {
            // Create a mail session
            Properties props = new Properties();
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", "" + smtpPort);
            Session session = Session.getInstance(props, null);

            // Construct the message
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(sender));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(birthdayMessage.recipient));
            msg.setSubject(birthdayMessage.subject);
            msg.setText(birthdayMessage.body);

            // Send the message
            Transport.send(msg);
        } catch (MessagingException e) {
            throw new MessageSendException(e);
        }
    }

    public static class MessageSendException extends RuntimeException {
        public MessageSendException(Throwable cause) {
            super(cause);
        }
    }
}
