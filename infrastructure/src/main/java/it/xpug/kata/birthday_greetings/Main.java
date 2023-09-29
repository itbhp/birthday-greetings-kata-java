package it.xpug.kata.birthday_greetings;

import it.xpug.kata.birthday_greetings.adapters.FileEmployeeRepository;
import it.xpug.kata.birthday_greetings.adapters.MailMessageService;
import it.xpug.kata.birthday_greetings.application.BirthdayService;

import java.io.IOException;
import java.io.InputStream;

import static it.xpug.kata.birthday_greetings.models.OurDate.now;

public class Main {

    public static void main(String[] args) throws IOException {
        try (InputStream employeesStream = Main.class.getResourceAsStream("/employee_data.txt")) {
            BirthdayService service = new BirthdayService(
                    new MailMessageService("localhost", 25, "sender@here.com"),
                    new FileEmployeeRepository(employeesStream)
            );
            service.sendGreetingsToEmployeesWithBirthDay(now());
        }
    }

}
