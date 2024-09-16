package ru.effective_mobile.exception;

public class ClassAnnotationException extends RuntimeException {
    public ClassAnnotationException() {
        super("The class of the passed object is not annotated");
    }
}