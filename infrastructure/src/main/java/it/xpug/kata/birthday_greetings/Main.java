package it.xpug.kata.birthday_greetings;

public class Main {

	public static void main(String[] args) {
		BirthdayService service = new BirthdayService(
				new MailMessageService("localhost", 25, "sender@here.com"),
				new FileEmployeeRepository(Main.class.getResourceAsStream("/employee_data.txt"))
		);
		service.sendGreetings(new OurDate());
	}

}
