package com.multi.server.agent.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "agent")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "agent_ip")
    private String agentIp;

    @Column(name = "cpu_cores")
    private Integer cpuCores;

    @Column(name = "memory_size")
    private Double memorySize;

    @Column(name = "os_info")
    private String osInfo;

    @Builder
    public Agent(String agentIp, Integer cpuCores, Double memorySize, String osInfo) {
        this.agentIp = agentIp;
        this.cpuCores = cpuCores;
        this.memorySize = memorySize;
        this.osInfo = osInfo;
    }
}
