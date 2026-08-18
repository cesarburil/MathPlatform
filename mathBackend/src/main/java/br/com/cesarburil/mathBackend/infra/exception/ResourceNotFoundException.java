package br.com.cesarburil.mathBackend.infra.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException of(String resource, Long id) {
        return new ResourceNotFoundException(resource + " not found: " + id);
    }

    public static ResourceNotFoundException of(String resource, String key) {
        return new ResourceNotFoundException(resource + " not found: " + key);
    }
}
