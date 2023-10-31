package com.multi.server.agent.exception;

public class NoAgentException extends RuntimeException {
    public NoAgentException() {
        super();
    }

    public NoAgentException(String message) {
        super(message);
    }

    public NoAgentException(String message, Throwable th) {
        super(message, th);
    }
}
