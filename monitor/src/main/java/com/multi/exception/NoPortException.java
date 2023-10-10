package com.multi.exception;

public class NoPortException extends RuntimeException {
    public NoPortException() {
        super();
    }

    public NoPortException(String message) {
        super(message);
    }

    public NoPortException(String message, Throwable th) {
        super(message, th);
    }
}
