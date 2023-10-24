package com.multi;

import com.multi.exception.NoPortException;
import com.multi.service.MonitorService;
import com.multi.service.MonitorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {

        int port = -1;
        FileReader reader = new FileReader("core/src/main/resources/application.properties");
        Properties properties = new Properties();
        try {
            properties.load(reader);
            port = Integer.parseInt(properties.getProperty("server.port"));
        } catch (IOException | NumberFormatException e) {
            log.error("포트번호 가져오기 실패");
            throw new NoPortException("monitor : 포트번호 가져오기 실패");
        }

        WebServer webServer = new WebServer(port);

        XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

        PropertyHandlerMapping phm = new PropertyHandlerMapping();

        phm.addHandler(MonitorService.class.getName(), MonitorServiceImpl.class);
        xmlRpcServer.setHandlerMapping(phm);

        XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
        serverConfig.setEnabledForExtensions(true);
        serverConfig.setContentLengthOptional(false);
        log.info("RPC 서버 가동");
        webServer.start();
    }
}