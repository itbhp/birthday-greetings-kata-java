package it.xpug.kata.birthday_greetings;

public class Main {

	public static void main(String[] args) {
		BirthdayService service = new BirthdayService(
				new MessageService("localhost", 25, "sender@here.com"),
				new EmployeeRepository("employee_data.txt")
		);
		service.sendGreetings(new OurDate());
	}

}
