package com.test.app.crud.validations;

public class FieldErrorDetails {
    private String message;
    private Object received;

    // Constructor
    public FieldErrorDetails(String message, Object received) {
        this.message = message;
        this.received = received;
    }

    // Getters (necesarios para la serialización automática de JSON)
    public String getMessage() {
        return message;
    }

    public Object getReceived() {
        return received;
    }
}
