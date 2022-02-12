package org.lff.ssm.controller;

import org.lff.ssm.websocket.MsgSocketHandler;
import org.lff.ssm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ChatController {
    @Autowired
    private MsgSocketHandler msgSocketHandler;
    private static final String SUBSCRIBE_MESSAGE_URI = "/chat.message";
    private Map<Integer, User> users;

    // 发送消息
    @PostMapping("/sendMsg")
    @ResponseBody
    public Boolean sendMsg(Integer uid, String content, HttpSession session){
        User u = (User)session.getAttribute(uid.toString());
        if(u == null)
            return false;
        String message = u.getUsername() + "：" + content;
        System.out.println(message);
        //调用 websocketHandler 处理消息
        msgSocketHandler.sendMessageToUsers(new TextMessage(message));
        return true;
    }

}
