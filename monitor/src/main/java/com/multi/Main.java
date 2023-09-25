package com.multi;


import com.multi.service.service.service.MonitorService;
import com.multi.service.service.service.MonitorServiceImpl;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

public class Main {
    private static final int port = 8080;

    public static void main(String[] args) throws Exception {
        WebServer webServer = new WebServer(port);

        XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

        PropertyHandlerMapping phm = new PropertyHandlerMapping();

        /* You may also provide the handler classes directly,
         * like this:
         * phm.addHandler("Calculator",
         *     org.apache.xmlrpc.demo.Calculator.class);
         * phm.addHandler(org.apache.xmlrpc.demo.proxy.Adder.class.getName(),
         *     org.apache.xmlrpc.demo.proxy.AdderImpl.class);
         */
        phm.addHandler(MonitorService.class.getName(), MonitorServiceImpl.class);
        xmlRpcServer.setHandlerMapping(phm);

        XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
        serverConfig.setEnabledForExtensions(true);
        serverConfig.setContentLengthOptional(false);

        webServer.start();
    }
}