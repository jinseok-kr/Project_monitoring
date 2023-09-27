package com.multi.server.agent.service;

import com.multi.dto.AgentInfoDTO;
import com.multi.server.agent.dto.RegistAgentRequestDTO;
import com.multi.server.agent.entity.Agent;
import com.multi.server.agent.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@RequiredArgsConstructor
@Service
public class AgentServiceImpl implements AgentService{
    private static final int port = 8080;
    private static final Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);
    private final AgentRepository agentRepository;

    @Override
    public void registAgent(RegistAgentRequestDTO registAgentRequestDTO) {
        Agent agent = registAgentRequestDTO.toEntity();
        agent = agentRepository.save(agent);
    }

    @Override
    public AgentInfoDTO callAgent(String agentIp){
        //등록 요청 보내기
        // 1. 컴퓨터가 살아있는가
        // 2. agent 서비스가 켜져있는가
        // 둘다 통과했으면 요청 온다.
        // 실패하면 결과 안옴
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
            result = (AgentInfoDTO) client.execute("MonitorServiceImpl.getRegistInfo", new Object[0]);
        } catch (MalformedURLException e) {
            logger.info("URL 생성 실패");
        } catch (XmlRpcException e) {
            logger.info("등록 요청 실패");
        }
        return result;
    }
}
