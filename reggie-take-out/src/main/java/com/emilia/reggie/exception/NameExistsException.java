package com.emilia.reggie.exception;

public class NameExistsException extends RuntimeException{

    public NameExistsException(String message) {
        super(message);
    }
}
