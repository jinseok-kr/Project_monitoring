package com.multi.server.agent.dto;

import com.multi.server.agent.entity.Agent;
import com.multi.server.agent.entity.Metric;
import lombok.Builder;

@Builder
public record RegistMetricRequest(Long agentId, Double cpuLoad, Double memoryLoad) {
}
