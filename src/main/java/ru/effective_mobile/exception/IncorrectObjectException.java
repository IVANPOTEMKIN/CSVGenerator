package ru.effective_mobile.exception;

public class IncorrectObjectException extends RuntimeException {
    public IncorrectObjectException() {
        super("An invalid object was passed to the method");
    }
}