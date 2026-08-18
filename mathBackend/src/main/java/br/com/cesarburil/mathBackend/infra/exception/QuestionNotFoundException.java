package br.com.cesarburil.mathBackend.infra.exception;

public class QuestionNotFoundException extends RuntimeException {

    public QuestionNotFoundException(String message) {
        super(message);
    }
}
