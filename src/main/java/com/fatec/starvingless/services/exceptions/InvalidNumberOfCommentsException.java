package com.fatec.starvingless.services.exceptions;

public class InvalidNumberOfCommentsException extends RuntimeException {
    public InvalidNumberOfCommentsException(String message) {
        super(message);
    }
}
