package org.lff.ssm.controller;

import org.lff.ssm.model.User;
import org.lff.ssm.service.RelationService;
import org.lff.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private RelationService relationService;

    private Map<Integer, User> users = new HashMap<>();

    @PostMapping(value = "/login")
    @ResponseBody
    public User login(User user, Model model, HttpSession session){
        // 查询数据库对应的用户信息
        User query_user = userService.getUserById(user.getId());
        // 判断登录信息是否正确
        if(query_user == null || !query_user.getPassword().equals(user.getPassword())){
            return null;
        }
        // 查询并设置好友关系
        query_user.setFriendIds(relationService.getAllFriendIds(query_user));
        query_user.setFriendNames(getNamesByIds(query_user.getFriendIds()));

        // 将此用户保存到已登录用户map中
        users.put(query_user.getId(), query_user);
        // 将此用户保存到 session 缓存中
        session.setAttribute(query_user.getId().toString(), query_user);
        return query_user;
    }


    //注册用户
    @RequestMapping("/register")
    @ResponseBody
    public Boolean register(User user){
        if(userService.getUserById(user.getId()) == null)
            if(userService.addUser(user) > 0)
                return true;
        return false;
    }

    // 通过 id 获取指定用户
    @PostMapping("/getUserById")
    @ResponseBody
    public User getUserById(String uid){
        return users.get(Integer.parseInt(uid));
    }


    /**
     * 通过 id 得到 name
     * @param ids
     * @return
     */
    List<String> getNamesByIds(List<Integer> ids){
        List<String> names = new ArrayList<>();
        for(Integer id : ids){
            names.add(userService.getUserById(id).getUsername());
        }
        return names;
    }
}
