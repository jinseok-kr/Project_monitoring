package com.multi;

import com.multi.monitor.exception.NoPortException;
import com.multi.core.service.MonitorService;
import com.multi.core.service.MonitorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {

        int port = -1;
        ClassLoader cl;
        cl = Thread.currentThread().getContextClassLoader();
        URL url = cl.getResource("application.properties");
        Properties properties = new Properties();
        try (InputStream stream = url.openStream()) {
            properties.load(stream);
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