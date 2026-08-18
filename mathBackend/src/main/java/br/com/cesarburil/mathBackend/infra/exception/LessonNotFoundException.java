package br.com.cesarburil.mathBackend.infra.exception;

public class LessonNotFoundException extends RuntimeException {

    public LessonNotFoundException(String message) {
        super(message);
    }
}
