package br.com.cesarburil.mathBackend.infra.exception;

public class InvalidQuestionAlternativesException extends RuntimeException {

    public InvalidQuestionAlternativesException(String message) {
        super(message);
    }
}
