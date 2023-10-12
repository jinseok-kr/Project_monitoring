package com.multi.server.agent.repository;

import com.multi.server.agent.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, String>, CustomAgentRepository {
}
