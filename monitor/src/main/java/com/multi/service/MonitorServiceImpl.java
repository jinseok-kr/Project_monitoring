package com.multi.service;


import com.multi.dto.AgentInfoDTO;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class MonitorServiceImpl implements MonitorService {

    private static final long byte2giga = 1024 * 1024 * 1024;

    @Override
    public int getCpuCores() {
        int cpuCores = Runtime.getRuntime().availableProcessors();

        return cpuCores;
    }

    @Override
    public long getMemorySize() {
        OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long memorySize = osbean.getTotalMemorySize()/byte2giga;

        return memorySize;
    }

    @Override
    public String getOsInfo() {
        String osInfo = System.getProperty("os.name");

        return osInfo;
    }

    @Override
    public AgentInfoDTO getRegistInfo() {
        return AgentInfoDTO.builder()
                .cpuCores(getCpuCores())
                .memorySize(getMemorySize())
                .osInfo(getOsInfo())
                .build();
    }
}
