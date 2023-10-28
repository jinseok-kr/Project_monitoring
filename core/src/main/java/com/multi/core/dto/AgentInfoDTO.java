package com.multi.core.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record AgentInfoDTO (int cpuCores, long memorySize, String osInfo) implements Serializable {
}
