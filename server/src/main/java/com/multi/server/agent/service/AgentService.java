package com.multi.server.agent.service;

import com.multi.dto.AgentInfoDTO;
import com.multi.server.agent.dto.RegistAgentRequestDTO;

import java.util.List;

public interface AgentService {
    void registAgent(RegistAgentRequestDTO registAgentRequestDTO);

    AgentInfoDTO callAgent(String agentIp);
}
