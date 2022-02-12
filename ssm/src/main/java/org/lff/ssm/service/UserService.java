package org.lff.ssm.service;

import org.lff.ssm.mapper.UserMapper;
import org.lff.ssm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    //获取所有用户
    public List<User> getAllUsers(){
        return userMapper.getAllUsers();
    }

    //获取指定用户
    public User getUserById(Integer id){
        return userMapper.getUserById(id);
    }

    // 添加用户
    public Integer addUser(User user){
        return userMapper.addUser(user);
    }

}
