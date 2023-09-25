package com.multi.service.service.service;

public interface MonitorService {
    String getCpuCores();
    String getMemorySize();
    String getOsInfo();

    String[] getRegistInfo();
}
