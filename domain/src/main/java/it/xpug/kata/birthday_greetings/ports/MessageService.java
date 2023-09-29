package it.xpug.kata.birthday_greetings.ports;

import it.xpug.kata.birthday_greetings.models.BirthdayMessage;

public interface MessageService {
    void send(BirthdayMessage birthdayMessage);
}
