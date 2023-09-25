package com.multi.server.agent.dto;

import com.multi.server.agent.entity.Agent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistAgentRequestDTO {
    private String agentIp;

    private String core;

    private String memory;

    private String os;

    public RegistAgentRequestDTO(String agentIp, String core, String memory, String os) {
        this.agentIp = agentIp;
        this.core = core;
        this.memory = memory;
        this.os = os;
    }

    public Agent toEntity() {
        return Agent.builder()
                .agentIp(agentIp)
                .core(core)
                .memory(memory)
                .os(os)
                .build();
    }
}
