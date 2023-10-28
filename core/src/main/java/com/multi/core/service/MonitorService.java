package com.multi.core.service;

import com.multi.core.dto.AgentInfoDTO;

public interface MonitorService {
    int getCpuCores();
    long getMemorySize();
    String getOsInfo();

    AgentInfoDTO getRegistInfo();
}
