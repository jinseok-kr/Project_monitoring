package com.multi.server.agent.dto;

import lombok.Builder;

@Builder
public record AgentsSearchDTO (String agentIp, Integer cpuCores, Long memorySize, String osInfo) {
}
