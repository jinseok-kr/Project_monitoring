package com.multi.server.agent.repository;

import static com.multi.server.agent.entity.QAgent.agent;

import com.multi.server.agent.dto.AgentDTO;
import com.multi.server.agent.dto.AgentsSearchDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomAgentRepositoryImpl implements CustomAgentRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<AgentDTO> findAgentsByFilter(AgentsSearchDTO agentsSearchDTO) {
        return queryFactory
                .select(Projections.constructor(AgentDTO.class,
                        agent.agentIp,
                        agent.cpuCores,
                        agent.memorySize,
                        agent.osInfo
                ))
                .from(agent)
                .where(
                        eqAgentIp(agentsSearchDTO.agentIp()),
                        eqCpuCores(agentsSearchDTO.cpuCores()),
                        eqMemorySize(agentsSearchDTO.memorySize()),
                        containsOsInfo(agentsSearchDTO.osInfo())

                ).fetch();
    }

    private BooleanExpression eqAgentIp(String agentIp) {
        return StringUtils.isNullOrEmpty(agentIp) ? null : agent.agentIp.eq(agentIp);
    }

    private BooleanExpression eqCpuCores(Integer cpuCores) {
        return cpuCores == null ? null : agent.cpuCores.eq(cpuCores);
    }

    private BooleanExpression eqMemorySize(Double memorySize) {
        return memorySize == null ? null : agent.memorySize.eq(memorySize);
    }

    private BooleanExpression containsOsInfo(String osInfo) {
        return StringUtils.isNullOrEmpty(osInfo) ? null : agent.osInfo.contains(osInfo);
    }
}
