package com.multi.server.agent.service;

import com.multi.core.dto.AgentInfoDTO;
import com.multi.core.dto.AgentMetricDTO;
import com.multi.server.agent.dto.AgentDTO;
import com.multi.server.agent.dto.AgentIpDTO;
import com.multi.server.agent.dto.AgentsSearchDTO;
import com.multi.server.agent.entity.Agent;

import java.util.List;
import java.util.Optional;

public interface AgentService {
    void registAgent(AgentDTO agentDTO);

    AgentInfoDTO callAgent(AgentIpDTO agentIpDTO);

    Optional<Agent> findByAgentIp(String agentIp);

    List<AgentDTO> getAgentsList(AgentsSearchDTO agentsSearchDTO);

    AgentMetricDTO getAgentMetric(Long id);
}
