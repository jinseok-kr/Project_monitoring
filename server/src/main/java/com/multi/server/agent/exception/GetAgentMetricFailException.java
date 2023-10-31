package com.multi.server.agent.exception;

public class GetAgentMetricFailException extends RuntimeException {
    public GetAgentMetricFailException() {
        super();
    }

    public GetAgentMetricFailException(String message) {
        super(message);
    }

    public GetAgentMetricFailException(String message, Throwable th) {
        super(message, th);
    }
}
