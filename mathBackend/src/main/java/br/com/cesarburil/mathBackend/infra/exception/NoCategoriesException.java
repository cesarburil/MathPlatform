package br.com.cesarburil.mathBackend.infra.exception;

public class NoCategoriesException extends RuntimeException {

    public NoCategoriesException(String message) {
        super(message);
    }
}
