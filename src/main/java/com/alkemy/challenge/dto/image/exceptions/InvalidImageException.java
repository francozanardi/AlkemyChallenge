package com.alkemy.challenge.dto.image.exceptions;

public class InvalidImageException extends RuntimeException {
    public InvalidImageException(String message) {
        super(message);
    }
}
