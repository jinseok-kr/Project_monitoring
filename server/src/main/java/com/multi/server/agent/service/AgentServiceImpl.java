package com.multi.server.agent.service;

import com.multi.server.agent.dto.RegistAgentRequestDTO;
import com.multi.server.agent.entity.Agent;
import com.multi.server.agent.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AgentServiceImpl implements AgentService{

    private final AgentRepository agentRepository;

    @Override
    public void registAgent(RegistAgentRequestDTO registAgentRequestDTO) {
        Agent agent = registAgentRequestDTO.toEntity();
        agent = agentRepository.save(agent);
    }
}
