package app.services;

public class ValidationException extends Exception {
    public ValidationException(String name) {
        super(name);
    }
}
