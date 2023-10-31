package com.multi.server.agent.repository;

import com.multi.server.agent.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long>, CustomAgentRepository {
    Optional<Agent> findByAgentIp(String agentIp);
}
