package com.salesianostriana.dam.exception;

public class UserException extends StorageException{
    public UserException(String message, Exception e) {
        super(message, e);
    }

    public UserException(String message) {
        super(message);
    }
}
