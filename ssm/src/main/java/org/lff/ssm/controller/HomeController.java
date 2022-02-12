package org.lff.ssm.controller;

import org.lff.ssm.model.User;
import org.lff.ssm.service.RelationService;
import org.lff.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
public class HomeController {
    @Autowired
    private RelationService relationService;
    @Autowired
    private UserService userService;
    private Map<Integer, User> users = new HashMap<>();

    //添加好友
    @PostMapping("/addFriendById")
    @ResponseBody
    public String addFriendById( Integer uid, @RequestParam Integer fid, HttpSession session) {
        User curUser = (User) session.getAttribute(uid.toString());
        Integer id1 = curUser.getId(), id2 = fid;
        if(curUser.getFriendIds() != null && curUser.getFriendIds().contains(id2))
            return "-1";    // 已存在此好友
        User fu = userService.getUserById(id2);
        if(fu == null)
            return "-2";    // 不存在此人
        if(relationService.addRelation(id1, id2)) {
            curUser.addFriend(id1, fu.getUsername());
            return fu.getUsername();    // 添加成功，返回其用户名
        }
        return "0";     // 未知原因添加失败
    }

    //删除好友
    @PostMapping("/delFriendById")
    @ResponseBody
    public Boolean delFriendById(Integer uid, @RequestParam Integer fid, HttpSession session){
        User curUser = (User) session.getAttribute(uid.toString());
        if(curUser != null) {
            relationService.delFriendById(curUser.getId(), fid);
            curUser.delFriend(fid, userService.getUserById(fid).getUsername());
            session.setAttribute(curUser.getId().toString(), curUser);
            return true;
        }
        return false;
    }

    // 退出登录 清除缓存
    @PostMapping("/exit")
    public void exit(Integer uid, HttpSession session){
        session.removeAttribute(uid.toString());
        users.remove(uid);
    }

}
