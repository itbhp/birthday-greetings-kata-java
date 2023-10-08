package it.xpug.kata.birthday_greetings.adapters;

import it.xpug.kata.birthday_greetings.exceptions.MessageSendException;
import it.xpug.kata.birthday_greetings.models.Employee;
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
    public void sendMessageTo(Employee employee) {
        try {
            // Create a mail session
            Session session = createSession();

            // Construct the message
            Message msg = messageFor(employee, session);

            // Send the message
            Transport.send(msg);
        } catch (MessagingException e) {
            throw new MessageSendException(e);
        }
    }

    private Message messageFor(Employee employee, Session session) throws MessagingException {
        String body = "Happy Birthday, dear %NAME%!".replace("%NAME%", employee.getFirstName());
        String recipient = employee.getEmail();
        String subject = "Happy Birthday!";
        return createMessage(session, recipient, subject, body);
    }

    private Message createMessage(Session session, String recipient, String subject, String body) throws MessagingException {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(sender));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        msg.setSubject(subject);
        msg.setText(body);
        return msg;
    }

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", "" + smtpPort);
        return Session.getInstance(props, null);
    }

}
