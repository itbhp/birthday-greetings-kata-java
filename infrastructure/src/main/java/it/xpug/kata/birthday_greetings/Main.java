package it.xpug.kata.birthday_greetings;

import java.io.IOException;
import java.io.InputStream;

import static it.xpug.kata.birthday_greetings.OurDate.now;

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
