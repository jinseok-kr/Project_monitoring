package com.multi.server.agent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity(name = "agent")
public class Agent {
    @Id
    @Column(name = "agent_ip")
    private String agentIp;

    @Column(name = "core")
    private String core;

    @Column(name = "memory")
    private String memory;

    @Column(name = "os")
    private String os;



    @Builder
    public Agent(String agentIp, String core, String memory, String os) {
        this.agentIp = agentIp;
        this.core = core;
        this.memory = memory;
        this.os = os;
    }
}
