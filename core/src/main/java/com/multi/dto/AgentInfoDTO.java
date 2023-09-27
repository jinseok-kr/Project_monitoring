package com.multi.dto;

import lombok.Builder;

@Builder
public record AgentInfoDTO (int cpuCores, long memorySize, String osInfo) {
}
