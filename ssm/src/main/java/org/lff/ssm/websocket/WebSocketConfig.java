package org.lff.ssm.websocket;

import org.lff.ssm.websocket.MsgSocketHandler;
import org.lff.ssm.websocket.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private MsgSocketHandler msgSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 配置webSocket服务端接口、Handler、拦截器
        registry.addHandler(msgSocketHandler, "/webSocketServer")
                .addInterceptors(new MyInterceptor()).setAllowedOrigins("*");
        registry.addHandler(msgSocketHandler,
                "/sockjs/webSocketServer")
                .addInterceptors(new MyInterceptor())
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
