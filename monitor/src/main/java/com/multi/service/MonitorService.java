package com.multi.service;

import com.multi.dto.AgentInfoDTO;

public interface MonitorService {
    int getCpuCores();
    long getMemorySize();
    String getOsInfo();

    AgentInfoDTO getRegistInfo();
}
