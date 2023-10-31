package com.multi.server.agent.service;

import com.multi.core.dto.AgentInfoDTO;
import com.multi.core.dto.AgentMetricDTO;
import com.multi.server.agent.dto.AgentDTO;
import com.multi.server.agent.dto.AgentIpDTO;
import com.multi.server.agent.dto.AgentMetricRequestDTO;
import com.multi.server.agent.dto.AgentsSearchDTO;

import java.util.List;

public interface AgentService {
    void registAgent(AgentDTO agentDTO);

    AgentInfoDTO callAgent(AgentIpDTO agentIpDTO);

    List<AgentDTO> getAgentsList(AgentsSearchDTO agentsSearchDTO);

    AgentMetricDTO getAgentMetric(AgentMetricRequestDTO agentMetricRequestDTO);
}
