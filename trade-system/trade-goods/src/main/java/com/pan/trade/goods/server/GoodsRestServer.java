package com.pan.trade.goods.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created by Loren on 2018/5/16.
 */
public class GoodsRestServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8081);
        ServletContextHandler springHandler = new ServletContextHandler();
        springHandler.setContextPath("/goods");
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setConfigLocation("classpath:spring-web-goods.xml");
        springHandler.addEventListener(new ContextLoaderListener(context));
        springHandler.addServlet(new ServletHolder(new DispatcherServlet(context)),"/*");
        server.setHandler(springHandler);
        server.start();
        server.join();

    }

}
