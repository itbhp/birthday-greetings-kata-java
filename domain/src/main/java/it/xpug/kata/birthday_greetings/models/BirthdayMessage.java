package it.xpug.kata.birthday_greetings.models;

public class BirthdayMessage {
    public final String recipient;
    public final String subject;
    public final String body;

    public BirthdayMessage(Employee employee) {
        this.recipient = employee.getEmail();
        this.subject = "Happy Birthday!";
        this.body = "Happy Birthday, dear %NAME%!".replace("%NAME%", employee.getFirstName());
    }
}
