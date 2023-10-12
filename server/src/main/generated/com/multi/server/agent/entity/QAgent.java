package com.multi.server.agent.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAgent is a Querydsl query type for Agent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAgent extends EntityPathBase<Agent> {

    private static final long serialVersionUID = -2092943092L;

    public static final QAgent agent = new QAgent("agent");

    public final StringPath agentIp = createString("agentIp");

    public final NumberPath<Integer> cpuCores = createNumber("cpuCores", Integer.class);

    public final NumberPath<Long> memorySize = createNumber("memorySize", Long.class);

    public final StringPath osInfo = createString("osInfo");

    public QAgent(String variable) {
        super(Agent.class, forVariable(variable));
    }

    public QAgent(Path<? extends Agent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAgent(PathMetadata metadata) {
        super(Agent.class, metadata);
    }

}

