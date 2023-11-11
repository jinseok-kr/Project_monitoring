package com.multi.server.agent.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "metric")
public class Metric {
    @Id
    @Column(name = "agent_id")
    private Long id;

    @Column(name = "cpu_load")
    private Double cpuLoad;

    @Column(name = "memory_load")
    private Double memoryLoad;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "agent_id")
    private Agent agent;

    @Builder
    public Metric(Double cpuLoad, Double memoryLoad, Agent agent) {
        this.cpuLoad = cpuLoad;
        this.memoryLoad = memoryLoad;
        this.agent = agent;
    }

    public void updateMetric(Double cpuLoad, Double memoryLoad) {
        this.cpuLoad = cpuLoad;
        this.memoryLoad = memoryLoad;
    }
}
