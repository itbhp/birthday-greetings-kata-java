package it.xpug.kata.birthday_greetings;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

public class BirthdayService {

	public void sendGreetings(String fileName, XDate xDate, String smtpHost, int smtpPort)
			throws IOException, ParseException, MessagingException {
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		in.readLine();
		String str = "";
		while ((str = in.readLine()) != null) {
			String[] employeeData = str.split(", ");
			Employee employee = new Employee(employeeData[1], employeeData[0], employeeData[2], employeeData[3]);
			if (employee.isBirthday(xDate)) {
				String recipient = employee.getEmail();
				String body = "Happy Birthday, dear %NAME%!".replace("%NAME%", employee.getFirstName());
				String subject = "Happy Birthday!";
				final MessageService messageService = new MessageService(smtpHost, smtpPort);
				messageService.send(recipient, subject, body);
			}
		}
	}

	private static void sendMessage(String smtpHost, int smtpPort, String sender, String subject, String body, String recipient) throws MessagingException {
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
	}

	private static class MessageService {
		private final String smtpHost;
		private final int smtpPort;

		public MessageService(String smtpHost, int smtpPort) {
			this.smtpHost = smtpHost;
			this.smtpPort = smtpPort;
		}

		public void send(String recipient1, String subject1, String body1) throws MessagingException {
			// Create a mail session
			Properties props = new Properties();
			props.put("mail.smtp.host", smtpHost);
			props.put("mail.smtp.port", "" + smtpPort);
			Session session = Session.getInstance(props, null);

			// Construct the message
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("sender@here.com"));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient1));
			msg.setSubject(subject1);
			msg.setText(body1);

			// Send the message
			Transport.send(msg);
		}
	}
}
