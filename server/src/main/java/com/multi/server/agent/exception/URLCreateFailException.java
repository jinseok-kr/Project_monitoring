package com.multi.server.agent.exception;

public class URLCreateFailException extends RuntimeException {
    public URLCreateFailException() {
        super();
    }

    public URLCreateFailException(String message) {
        super(message);
    }

    public URLCreateFailException(String message, Throwable th) {
        super(message, th);
    }
}
