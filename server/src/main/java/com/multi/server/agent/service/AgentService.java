package com.multi.server.agent.service;

import com.multi.core.dto.AgentInfoDTO;
import com.multi.server.agent.dto.AgentDTO;
import com.multi.server.agent.dto.AgentsSearchDTO;

import java.util.List;

public interface AgentService {
    void registAgent(AgentDTO agentDTO);

    AgentInfoDTO callAgent(String agentIp);

    List<AgentDTO> getAgentsList(AgentsSearchDTO agentsSearchDTO);
}
