package it.xpug.kata.birthday_greetings.ports;

public interface MessageService {
    void send(String subject, String recipient, String body);
}
