package com.multi.core.service;

import com.multi.core.dto.AgentInfoDTO;
import com.multi.core.dto.AgentMetricDTO;

public interface MonitorService {
    int getCpuCores();
    double getMemorySize();
    String getOsInfo();
    AgentInfoDTO getRegistInfo();

    double getCpuLoad();
    double getMemoryLoad();
    AgentMetricDTO getAgentMetric();
}
