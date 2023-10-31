package com.multi.core.service;


import com.multi.core.dto.AgentInfoDTO;
import com.multi.core.dto.AgentMetricDTO;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class MonitorServiceImpl implements MonitorService {

    private static final double byte2giga = 1024 * 1024 * 1024;

    @Override
    public int getCpuCores() {
        int cpuCores = Runtime.getRuntime().availableProcessors();

        return cpuCores;
    }

    @Override
    public double getMemorySize() {
        OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double memorySize = osbean.getTotalMemorySize()/byte2giga;

        return Math.round(memorySize * 100) / 100.0;
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

    @Override
    public double getCpuLoad() {
        OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double cpuLoad = osbean.getCpuLoad();
        return Math.round(cpuLoad * 100 * 100) / 100.0;
    }

    @Override
    public double getMemoryLoad() {
        OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double memoryLoad = (osbean.getTotalMemorySize() - osbean.getFreeMemorySize()) * 1.0 / osbean.getTotalMemorySize();
        return Math.round(memoryLoad * 100 * 100) / 100.0;
    }

    @Override
    public AgentMetricDTO getAgentMetric() {
        return AgentMetricDTO.builder()
                .cpuLoad(getCpuLoad())
                .memoryLoad(getMemoryLoad())
                .build();
    }
}
