package com.multi.core.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record AgentInfoDTO (Integer cpuCores, Double memorySize, String osInfo) implements Serializable {
}
