package com.multi.server.agent.controller;


import com.multi.server.agent.dto.RegistAgentRequestDTO;
import com.multi.server.agent.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/agent")
public class AgentController {

    private final AgentService agentService;

    @PostMapping("/regist")
    public ResponseEntity<RegistAgentRequestDTO> regist(@RequestBody RegistAgentRequestDTO dto) {

        agentService.registAgent(dto);
        return ResponseEntity.ok()
                .body(dto);
    }
}
