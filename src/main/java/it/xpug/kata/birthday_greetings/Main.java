package it.xpug.kata.birthday_greetings;

import java.io.*;
import java.text.ParseException;

import javax.mail.*;

public class Main {

	public static void main(String[] args) throws IOException, ParseException {
		BirthdayService service = new BirthdayService(
				new MessageService("localhost", 25, "sender@here.com"),
				new EmployeeRepository("employee_data.txt")
		);
		service.sendGreetings(new OurDate());
	}

}
