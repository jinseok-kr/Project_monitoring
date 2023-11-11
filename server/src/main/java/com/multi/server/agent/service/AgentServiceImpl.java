package com.multi.server.agent.service;

import com.multi.core.dto.AgentMetricDTO;
import com.multi.server.agent.config.ConfigProperties;
import com.multi.server.agent.dto.AgentDTO;
import com.multi.server.agent.dto.AgentIpDTO;
import com.multi.server.agent.dto.AgentsSearchDTO;
import com.multi.server.agent.dto.RegistMetricRequest;
import com.multi.server.agent.entity.Agent;
import com.multi.server.agent.entity.Metric;
import com.multi.server.agent.exception.AgentRegistFailException;
import com.multi.server.agent.exception.GetAgentMetricFailException;
import com.multi.server.agent.exception.NoAgentException;
import com.multi.server.agent.exception.URLCreateFailException;
import com.multi.server.agent.repository.AgentRepository;
import com.multi.core.dto.AgentInfoDTO;
import com.multi.core.service.MonitorService;
import com.multi.server.agent.repository.MetricRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.apache.xmlrpc.client.util.ClientFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.UndeclaredThrowableException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@EnableConfigurationProperties(ConfigProperties.class)
public class AgentServiceImpl implements AgentService {

    private final ConfigProperties configProperties;
    private final AgentRepository agentRepository;
    private final MetricRepository metricRepository;

    //에이전트 DB 등록
    @Override
    @Transactional
    public void registAgent(AgentDTO agentDTO) {
        Agent agent = agentDTO.toEntity();
        agent = agentRepository.save(agent);
        log.info("에이전트 정보 DB 저장");
    }

    //에이전트 등록 요청
    @Override
    public AgentInfoDTO callAgent(AgentIpDTO agentIpDTO) {
        AgentInfoDTO result = null;
        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL(String.format("http://%s:%d/xmlrpc",agentIpDTO.agentIp(), configProperties.getPort())));
            config.setEnabledForExtensions(true);
            config.setConnectionTimeout(60 * 1000);
            config.setReplyTimeout(60 * 1000);

            XmlRpcClient client = new XmlRpcClient();
            // use Commons HttpClient as transport
            client.setTransportFactory(new XmlRpcCommonsTransportFactory(client));
            // set configuration
            client.setConfig(config);
            ClientFactory factory = new ClientFactory(client);
            MonitorService monitorService = (MonitorService) factory.newInstance(MonitorService.class);
            result = monitorService.getRegistInfo();
            if (result == null) {
                log.error("에이전트 등록 요청 실패");
                throw new AgentRegistFailException();
            }
        } catch (MalformedURLException e) {
            log.warn("URL 생성 실패");
            throw new URLCreateFailException();
        } catch (UndeclaredThrowableException e) {
            log.warn("에이전트 등록 요청 실패");
            throw new AgentRegistFailException();
        }
        log.info("에이전트 등록 요청 성공");
        return result;
    }

    //에이전트 목록 조회
    @Override
    @Transactional
    public List<AgentDTO> getAgentsList(AgentsSearchDTO agentsSearchDTO) {
        log.info("에이전트 목록 검색");
        return agentRepository.findAgentsByFilter(agentsSearchDTO);
    }

    //에이전트 메트릭 정보 요청
    @Override
    public AgentMetricDTO getAgentMetric(Long id) {
        AgentMetricDTO result = null;
        Agent target = agentRepository.findById(id).orElseThrow(NoAgentException::new);
        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL(String.format("http://%s:%d/xmlrpc",target.getAgentIp(), configProperties.getPort())));
            config.setEnabledForExtensions(true);
            config.setConnectionTimeout(60 * 1000);
            config.setReplyTimeout(60 * 1000);

            XmlRpcClient client = new XmlRpcClient();
            // use Commons HttpClient as transport
            client.setTransportFactory(new XmlRpcCommonsTransportFactory(client));
            // set configuration
            client.setConfig(config);
            ClientFactory factory = new ClientFactory(client);
            MonitorService monitorService = (MonitorService) factory.newInstance(MonitorService.class);
            result = monitorService.getAgentMetric();
            if (result == null) {
                log.error("에이전트 메트릭 정보 가져오기 실패");
                throw new GetAgentMetricFailException();
            }
        } catch (MalformedURLException e) {
            log.warn("URL 생성 실패");
            throw new URLCreateFailException();
        } catch (UndeclaredThrowableException e) {
            log.warn("에이전트 메트릭 정보 가져오기 실패");
            throw new GetAgentMetricFailException();
        }
        log.info("에이전트 메트릭 정보 가져오기 성공");
        return result;
    }

    @Override
    @Transactional
    public Optional<Agent> findByAgentIp(String agentIp) {
        return agentRepository.findByAgentIp(agentIp);
    }

    @Override
    @Transactional
    public Optional<Agent> findByAgentId(Long id) {
        return agentRepository.findById(id);
    }

    //에이전트 메트릭 정보 DB 저장
    @Override
    @Transactional
    public void registMetric(RegistMetricRequest registMetricRequest) {
        Metric metric = metricRepository.findById(registMetricRequest.id()).orElse(
                Metric.builder()
                        .cpuLoad(registMetricRequest.cpuLoad())
                        .memoryLoad(registMetricRequest.memoryLoad())
                        .agent(agentRepository.findById(registMetricRequest.id()).orElseThrow(NoAgentException::new))
                        .build()
        );
        metric.updateMetric(registMetricRequest.cpuLoad(), registMetricRequest.memoryLoad());
        metricRepository.save(metric);
        log.info("메트릭 정보 저장");
    }

    @Scheduled(cron = "0/10 * * * * *", zone = "Asia/Seoul")
    @Transactional
    public void scheduledRegistMetric() {
        log.info("스케쥴러 호출");
        List<Agent> agentList = agentRepository.findAll();
        for (Agent agent : agentList) {
            log.info(agent.getId() + "번 agent 호출");
            AgentMetricDTO agentMetric = this.getAgentMetric(agent.getId());
            RegistMetricRequest dto = RegistMetricRequest.builder()
                    .id(agent.getId())
                    .cpuLoad(agentMetric.cpuLoad())
                    .memoryLoad(agentMetric.memoryLoad())
                    .build();
            this.registMetric(dto);
        }
    }
}