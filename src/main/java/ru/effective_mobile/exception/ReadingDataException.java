package ru.effective_mobile.exception;

public class ReadingDataException extends RuntimeException {
    public ReadingDataException() {
        super("Error when reading data from a file");
    }
}