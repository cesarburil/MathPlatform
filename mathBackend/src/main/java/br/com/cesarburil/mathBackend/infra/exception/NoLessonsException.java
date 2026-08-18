package br.com.cesarburil.mathBackend.infra.exception;

public class NoLessonsException extends RuntimeException {

    public NoLessonsException(String message) {
        super(message);
    }
}
