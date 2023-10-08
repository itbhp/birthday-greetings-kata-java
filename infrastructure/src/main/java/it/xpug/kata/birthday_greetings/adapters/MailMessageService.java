package it.xpug.kata.birthday_greetings.adapters;

import it.xpug.kata.birthday_greetings.exceptions.MessageSendException;
import it.xpug.kata.birthday_greetings.ports.MessageService;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailMessageService implements MessageService {
    private final String smtpHost;
    private final int smtpPort;
    private final String sender;

    public MailMessageService(String smtpHost, int smtpPort, String sender) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.sender = sender;
    }

    @Override
    public void send(String subject, String recipient, String body){
        try {
            // Create a mail session
            Properties props = new Properties();
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", "" + smtpPort);
            Session session = Session.getInstance(props, null);

            // Construct the message
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(sender));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            msg.setSubject(subject);
            msg.setText(body);

            // Send the message
            Transport.send(msg);
        } catch (MessagingException e) {
            throw new MessageSendException(e);
        }
    }

}
