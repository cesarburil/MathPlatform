package br.com.cesarburil.mathBackend.infra.exception;

public class NoQuestionsException extends RuntimeException {

    public NoQuestionsException(String message) {
        super(message);
    }
}
