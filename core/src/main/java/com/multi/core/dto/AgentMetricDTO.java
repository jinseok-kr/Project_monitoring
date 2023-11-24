package com.multi.core.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record AgentMetricDTO (Double cpuLoad, Double memoryLoad) implements Serializable {
}
