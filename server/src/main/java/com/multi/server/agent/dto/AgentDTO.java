package com.multi.server.agent.dto;

import com.multi.server.agent.entity.Agent;
import lombok.Builder;

@Builder
public record AgentDTO (String agentIp, Integer cpuCores, Long memorySize, String osInfo) {
    public Agent toEntity() {
        return Agent.builder()
                .agentIp(agentIp)
                .cpuCores(cpuCores)
                .memorySize(memorySize)
                .osInfo(osInfo)
                .build();
    }
}
