package com.cwpark.petmap.petmap.config.exception;

public class NoSuchDataException extends Exception{
    public NoSuchDataException() {};
    public NoSuchDataException(String message) {
        super(message);
    }
}
