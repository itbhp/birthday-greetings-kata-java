package it.xpug.kata.birthday_greetings;

import java.util.List;

public class BirthdayService {

    private final MessageService messageService;
    private final EmployeeRepository employeeRepository;

    public BirthdayService(MessageService messageService, EmployeeRepository employeeRepository) {
        this.messageService = messageService;
        this.employeeRepository = employeeRepository;
    }

    public void sendGreetingsToEmployeesWithBirthDay(OurDate ourDate) {
        List<Employee> employees = employeeRepository.getAll();
        for (Employee employee : employees) {
            if (employee.isBirthday(ourDate)) {
                messageService.send(new BirthdayMessage(employee));
            }
        }
    }

}
