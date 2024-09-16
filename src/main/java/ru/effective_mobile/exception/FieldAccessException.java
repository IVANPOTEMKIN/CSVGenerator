package ru.effective_mobile.exception;

public class FieldAccessException extends RuntimeException {
    public FieldAccessException() {
        super("It is not possible to get a value from a class field");
    }
}