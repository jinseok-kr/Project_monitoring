package com.multi.server.agent.controller;


import com.multi.core.dto.AgentMetricDTO;
import com.multi.server.agent.dto.AgentDTO;
import com.multi.server.agent.dto.AgentIpDTO;
import com.multi.server.agent.dto.AgentsSearchDTO;
import com.multi.server.agent.dto.RegistMetricRequest;
import com.multi.server.agent.service.AgentService;
import com.multi.core.dto.AgentInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/agent")
public class AgentController {
    private final AgentService agentService;

    @PostMapping("/regist")
    public ResponseEntity registRequest(@RequestBody AgentIpDTO agentIpDTO) { //ip를 받아서 에이전트 서버에 등록요청
        if (agentService.findByAgentIp(agentIpDTO.agentIp()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 등록된 에이전트");
        }
        AgentInfoDTO agentInfo = agentService.callAgent(agentIpDTO);
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
    public ResponseEntity<List<AgentDTO>> searchAgents(AgentsSearchDTO agentsSearchDTO) {
        List<AgentDTO> agents = agentService.getAgentsList(agentsSearchDTO);
        return ResponseEntity.ok(agents);
    }

    @GetMapping("/metric/{id}")
    public ResponseEntity showMetric(@PathVariable("id") Long id) {
        AgentMetricDTO agentMetric = agentService.getAgentMetric(id);
        RegistMetricRequest dto = RegistMetricRequest.builder()
                .agentId(id)
                .cpuLoad(agentMetric.cpuLoad())
                .memoryLoad(agentMetric.memoryLoad())
                .build();
        agentService.registMetric(dto);

        return ResponseEntity.ok(agentMetric);
    }
}
