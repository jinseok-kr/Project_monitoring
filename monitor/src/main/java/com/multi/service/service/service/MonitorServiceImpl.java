package com.multi.service.service.service;


import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class MonitorServiceImpl implements MonitorService {

    @Override
    public String getCpuCores() {
        String cpuCores = String.valueOf(Runtime.getRuntime().availableProcessors());

        return cpuCores;
    }

    @Override
    public String getMemorySize() {
        OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        String memorySize = String.format("%.2f", osbean.getTotalMemorySize()/1024/1024/1024);

        return memorySize;
    }

    @Override
    public String getOsInfo() {
        String osInfo = System.getProperty("os.name");

        return osInfo;
    }

    @Override
    public String[] getRegistInfo() {
        String[] info = new String[3];
        info[0] = getCpuCores();
        info[1] = getMemorySize();
        info[2] = getOsInfo();
        return info;
    }
}
