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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metric_id")
    Long metricId;

    @Column(name = "cpu_load")
    Double cpuLoad;

    @Column(name = "memory_load")
    Double memoryLoad;

    @ManyToOne
    @JoinColumn(name = "id")
    Agent agent;

    @Builder
    public Metric(Double cpuLoad, Double memoryLoad, Agent agent) {
        this.cpuLoad = cpuLoad;
        this.memoryLoad = memoryLoad;
        this.agent = agent;
    }
}
