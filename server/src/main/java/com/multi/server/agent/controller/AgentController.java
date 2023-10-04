package com.multi.server.agent.controller;


import com.multi.server.agent.dto.RegistAgentRequestDTO;
import com.multi.server.agent.service.AgentService;
import com.multi.dto.AgentInfoDTO;
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
        AgentInfoDTO agentInfo = agentService.callAgent(ip);
        RegistAgentRequestDTO dto = RegistAgentRequestDTO.builder()
                .agentIp(ip)
                .cpuCores(agentInfo.cpuCores())
                .memorySize(agentInfo.memorySize())
                .osInfo(agentInfo.osInfo())
                .build();
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
