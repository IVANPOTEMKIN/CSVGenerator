package ru.effective_mobile.exception;

public class WritingDataException extends RuntimeException {
    public WritingDataException() {
        super("Error when writing data to a file");
    }
}