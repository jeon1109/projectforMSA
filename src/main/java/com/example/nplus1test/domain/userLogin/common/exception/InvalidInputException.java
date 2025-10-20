package com.example.nplus1test.domain.userLogin.common.exception;

public class InvalidInputException extends RuntimeException {

    private final String fieldName;

    public InvalidInputException() {
        super("Invalid Input");
        this.fieldName = "";
    }

    public InvalidInputException(String fieldName) {
        super("Invalid Input");
        this.fieldName = fieldName;
    }

    public InvalidInputException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
