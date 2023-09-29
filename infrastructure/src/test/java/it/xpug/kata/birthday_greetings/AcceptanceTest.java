package it.xpug.kata.birthday_greetings;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import it.xpug.kata.birthday_greetings.adapters.FileEmployeeRepository;
import it.xpug.kata.birthday_greetings.adapters.MailMessageService;
import it.xpug.kata.birthday_greetings.application.BirthdayService;
import it.xpug.kata.birthday_greetings.models.OurDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;


public class AcceptanceTest {

	private static final int NONSTANDARD_PORT = 9999;
	private BirthdayService birthdayService;
	private SimpleSmtpServer mailServer;
	private InputStream employeeStream;

	@Before
	public void setUp() {
		mailServer = SimpleSmtpServer.start(NONSTANDARD_PORT);
		employeeStream = this.getClass().getResourceAsStream("/employee_data.txt");
		birthdayService = new BirthdayService(
				new MailMessageService("localhost", NONSTANDARD_PORT, "sender@here.com"),
				new FileEmployeeRepository(employeeStream)
		);
	}

	@After
	public void tearDown() throws Exception {
		mailServer.stop();
		if(employeeStream != null){
			employeeStream.close();
		}
		Thread.sleep(200);
	}

	@Test
	public void willSendGreetings_whenItsSomebodyBirthday() throws Exception {

		birthdayService.sendGreetingsToEmployeesWithBirthDay(new OurDate("2008/10/08"));

		assertEquals("message not sent?", 1, mailServer.getReceivedEmailSize());
		SmtpMessage message = (SmtpMessage) mailServer.getReceivedEmail().next();
		assertEquals("Happy Birthday, dear John!", message.getBody());
		assertEquals("Happy Birthday!", message.getHeaderValue("Subject"));
		String[] recipients = message.getHeaderValues("To");
		assertEquals(1, recipients.length);
		assertEquals("john.doe@foobar.com", recipients[0]);
	}

	@Test
	public void willNotSendEmailsWhenNobodyBirthday() throws Exception {
		birthdayService.sendGreetingsToEmployeesWithBirthDay(new OurDate("2008/01/01"));

		assertEquals("what? messages?", 0, mailServer.getReceivedEmailSize());
	}
}
