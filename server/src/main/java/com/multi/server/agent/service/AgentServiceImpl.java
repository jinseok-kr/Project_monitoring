package com.multi.server.agent.service;

import com.multi.server.agent.dto.RegistAgentRequestDTO;
import com.multi.server.agent.entity.Agent;
import com.multi.server.agent.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.apache.xmlrpc.client.util.ClientFactory;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@RequiredArgsConstructor
@Service
public class AgentServiceImpl implements AgentService{
    private static final int port = 8080;
    private final AgentRepository agentRepository;

    @Override
    public void registAgent(RegistAgentRequestDTO registAgentRequestDTO) {
        Agent agent = registAgentRequestDTO.toEntity();
        agent = agentRepository.save(agent);
    }

    @Override
    public String[] callAgent(String agentIp){
        //등록 요청 보내기
        // 1. 컴퓨터가 살아있는가
        // 2. agent 서비스가 켜져있는가
        // 둘다 통과했으면 요청 온다.
        // 실패하면 결과 안옴
        String[] result = null;
        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL("http://" + agentIp + ":" + port + "/xmlrpc"));
            config.setEnabledForExtensions(true);
            config.setConnectionTimeout(60 * 1000);
            config.setReplyTimeout(60 * 1000);

            XmlRpcClient client = new XmlRpcClient();
            // use Commons HttpClient as transport
            client.setTransportFactory(new XmlRpcCommonsTransportFactory(client));
            // set configuration
            client.setConfig(config);
            result = (String[]) client.execute("MonitorServiceImpl.getRegistInfo", new Object[0]);
        } catch (MalformedURLException e) {
            System.out.println("URL 생성 실패");
        } catch (XmlRpcException e) {
            System.out.println("등록 요청 실패");
        }
        return result;
    }
}
