package it.xpug.kata.birthday_greetings.ports;

import it.xpug.kata.birthday_greetings.models.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> getAll();
}
