package org.lff.ssm.websocket;

import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MsgSocketHandler extends TextWebSocketHandler {
    private static ConcurrentHashMap<String, WebSocketSession> users = new ConcurrentHashMap<>();
    private static Logger logger = LoggerFactory.getLogger(MsgSocketHandler.class);

    // 连接后需要做的事情
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 保存用户
        users.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 处理消息
        session.sendMessage(new TextMessage(message.getPayload()));
        System.out.println("服务器收到消息：" + message);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 连接关闭后清除缓存
        if(users.get(session.getId()) != null)
            users.remove(session.getId());
    }

//    public void sendMessageToUser(Integer fid, TextMessage message){
//        for(WebSocketSession userSession : users.values()){
//            if(userSession.getAttributes().get("user") == fid){
//                try {
//                    if (userSession.isOpen())
//                        userSession.sendMessage(message);
//
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//                break;
//            }
//        }
//    }

    // 向所有用户发送消息
    public void sendMessageToUsers(TextMessage message){
        for(WebSocketSession userSession : users.values()){
            try {
                if (userSession.isOpen())
                    userSession.sendMessage(message);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
