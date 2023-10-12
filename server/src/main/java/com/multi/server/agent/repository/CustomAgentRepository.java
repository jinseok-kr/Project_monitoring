package com.multi.server.agent.repository;

import com.multi.server.agent.dto.AgentDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomAgentRepository {
    List<AgentDTO> findAgentsByFilter(AgentDTO agentDTO);
}
