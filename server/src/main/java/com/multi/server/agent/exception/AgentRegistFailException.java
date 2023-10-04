package com.multi.server.agent.exception;

public class AgentRegistFailException extends RuntimeException {
    public AgentRegistFailException() {
        super();
    }

    public AgentRegistFailException(String message) {
        super(message);
    }

    public AgentRegistFailException(String message, Throwable th) {
        super(message, th);
    }
}
