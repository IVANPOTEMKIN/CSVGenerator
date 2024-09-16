package ru.effective_mobile.exception;

public class ListDataException extends RuntimeException{
    public ListDataException() {
        super("A null or empty list with data was passed");
    }
}