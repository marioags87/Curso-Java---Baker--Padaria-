package com.mario.bakery.model.exception;

public class ResponseException extends RuntimeException {
    public ResponseException (String message)
    {
        super(message);
    }
}
