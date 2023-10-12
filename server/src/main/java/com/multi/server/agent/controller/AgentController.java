package com.multi.server.agent.controller;


import com.multi.server.agent.dto.AgentDTO;
import com.multi.server.agent.dto.AgentIpDTO;
import com.multi.server.agent.service.AgentService;
import com.multi.dto.AgentInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/agent")
public class AgentController {
    private final AgentService agentService;

    @PostMapping("/regist")
    public ResponseEntity registRequest(@RequestBody AgentIpDTO agentIpDTO) { //ip를 받아서 에이전트 서버에 등록요청
        AgentInfoDTO agentInfo = agentService.callAgent(agentIpDTO.agentIp());
        AgentDTO dto = AgentDTO.builder()
                .agentIp(agentIpDTO.agentIp())
                .cpuCores(agentInfo.cpuCores())
                .memorySize(agentInfo.memorySize())
                .osInfo(agentInfo.osInfo())
                .build();
        agentService.registAgent(dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AgentDTO>> searchAgents(@RequestParam(required = false) String agentIp,
                                       @RequestParam(required = false) int cpuCores,
                                       @RequestParam(required = false) long memorySize,
                                       @RequestParam(required = false) String osInfo) {
        List<AgentDTO> agents = agentService.getAgentsList(new AgentDTO(agentIp, cpuCores, memorySize, osInfo));
        return ResponseEntity.ok(agents);
    }

//    @PostMapping("/regist") //에이전트가 보내온 정보를 DB에 등록
//    public ResponseEntity<RegistAgentRequestDTO> regist(@RequestBody RegistAgentRequestDTO dto) {
//
//        agentService.registAgent(dto);
//        return ResponseEntity.ok()
//                .body(dto);
//    }
}
