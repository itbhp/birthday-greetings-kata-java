package it.xpug.kata.birthday_greetings;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class BirthdayService {

    private final MessageService messageService;
    private final EmployeeRepository employeeRepository;

    public BirthdayService(MessageService messageService, EmployeeRepository employeeRepository) {
        this.messageService = messageService;
        this.employeeRepository = employeeRepository;
    }

    public void sendGreetings(OurDate ourDate)
            throws IOException, ParseException {
        List<Employee> employees = employeeRepository.getAll();
        for (Employee employee : employees) {
            if (employee.isBirthday(ourDate)) {
                messageService.send(new BirthdayMessage(employee));
            }
        }
    }

}
