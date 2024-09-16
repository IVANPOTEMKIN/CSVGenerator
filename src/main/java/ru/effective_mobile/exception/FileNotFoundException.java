package ru.effective_mobile.exception;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException() {
        super("The file cannot be read because it is empty or does not exist");
    }
}