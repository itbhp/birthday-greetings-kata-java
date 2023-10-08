package it.xpug.kata.birthday_greetings.ports;

import it.xpug.kata.birthday_greetings.models.Employee;

public interface MessageService {
    void sendMessageTo(Employee employee);
}
