package org.lff.ssm.mapper;

import org.lff.ssm.model.User;

import java.util.List;

public interface UserMapper {
    // 获取所有用户
    List<User> getAllUsers();
    // 获取指定用户
    User getUserById(Integer id);
    // 添加用户
    Integer addUser(User user);
}
