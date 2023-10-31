package com.multi.server.agent.advice;

import com.multi.server.agent.exception.AgentRegistFailException;
import com.multi.server.agent.exception.GetAgentMetricFailException;
import com.multi.server.agent.exception.URLCreateFailException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AgentExceptionAdvice {
    @ExceptionHandler(AgentRegistFailException.class)
    protected ResponseEntity agentRegistFail() {
        return ResponseEntity.internalServerError().body("에이전트 등록 요청 실패");
    }

    @ExceptionHandler(URLCreateFailException.class)
    protected ResponseEntity urlCreateFailException() {
        return ResponseEntity.internalServerError().body("URL 생성 실패");
    }

    @ExceptionHandler(GetAgentMetricFailException.class)
    protected ResponseEntity getAgentMetricFailException() {
        return ResponseEntity.internalServerError().body("에이전트 메트릭 정보 가져오기 실패");
    }
}
