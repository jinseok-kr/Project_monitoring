package com.multi.server.agent.controller;


import com.multi.server.agent.dto.RegistAgentRequestDTO;
import com.multi.server.agent.service.AgentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/agent")
public class AgentController {
    private final AgentService agentService;

    @GetMapping("/alive/{ip}")
    public ResponseEntity registRequest(@PathVariable String ip) { //ip를 받아서 에이전트 서버에 등록요청
        String[] agentInfo = agentService.callAgent(ip);
        if (agentInfo == null) return ResponseEntity.badRequest().body("등록 요청 실패");
        RegistAgentRequestDTO dto = new RegistAgentRequestDTO(ip, agentInfo[0], agentInfo[1], agentInfo[2]);
        agentService.registAgent(dto);
        return ResponseEntity.ok(dto);
    }

//    @PostMapping("/regist") //에이전트가 보내온 정보를 DB에 등록
//    public ResponseEntity<RegistAgentRequestDTO> regist(@RequestBody RegistAgentRequestDTO dto) {
//
//        agentService.registAgent(dto);
//        return ResponseEntity.ok()
//                .body(dto);
//    }
}
