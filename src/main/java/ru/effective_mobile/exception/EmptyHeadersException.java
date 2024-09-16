package ru.effective_mobile.exception;

public class EmptyHeadersException extends RuntimeException {
    public EmptyHeadersException() {
        super("The headers are empty because the class fields of the passed object are not annotated");
    }
}