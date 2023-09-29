package it.xpug.kata.birthday_greetings.exceptions;

public class MessageSendException extends RuntimeException {
    public MessageSendException(Throwable cause) {
        super(cause);
    }
}
