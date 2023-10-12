package com.multi.server.agent.service;

import com.multi.server.agent.dto.AgentDTO;
import com.multi.server.agent.entity.Agent;
import com.multi.server.agent.exception.AgentRegistFailException;
import com.multi.server.agent.exception.URLCreateFailException;
import com.multi.server.agent.repository.AgentRepository;
import com.multi.dto.AgentInfoDTO;
import com.multi.service.MonitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.apache.xmlrpc.client.util.ClientFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.UndeclaredThrowableException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AgentServiceImpl implements AgentService {
    private static final int port = 8080;
    private final AgentRepository agentRepository;

    //에이전트 DB 등록
    @Override
    public void registAgent(AgentDTO agentDTO) {
        Agent agent = agentDTO.toEntity();
        agent = agentRepository.save(agent);
    }

    //에이전트 등록 요청
    @Override
    public AgentInfoDTO callAgent(String agentIp) {
        AgentInfoDTO result = null;
        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL(String.format("http://%s:%d/xmlrpc",agentIp, port)));
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
        return result;
    }

    //에이전트 목록 조회
    @Override
    public List<AgentDTO> getAgentsList(AgentDTO agentDTO) {
        return agentRepository.findAgentsByFilter(agentDTO);
    }
}
