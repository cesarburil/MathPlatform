package br.com.cesarburil.mathBackend.infra.exception;

public class AnswerNotFoundException extends RuntimeException {

    public AnswerNotFoundException(String message) {
        super(message);
    }
}
