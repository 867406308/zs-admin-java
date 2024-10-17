package com.zs.common.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author 86740
 * 开启WebSocket支持
 */
@Configuration
public class WebsocketConfig {


    /**
     * ServerEndpointExporter类的作用是，会扫描所有的服务器端点，
     * 把带有@ServerEndpoint 注解的所有类都添加进来
     *
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
