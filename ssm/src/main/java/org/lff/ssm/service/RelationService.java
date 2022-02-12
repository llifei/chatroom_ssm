package org.lff.ssm.service;

import org.lff.ssm.mapper.RelationMapper;
import org.lff.ssm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationService {
    @Autowired
    private RelationMapper relationMapper;

    // 添加好友关系
    public Boolean addRelation(Integer id1, Integer id2){
        return relationMapper.addRelation(id1, id2);
    }

    // 获取所有好友id
    public List<Integer> getAllFriendIds(User user){
        return relationMapper.getAllFriendIds(user.getId());
    }

    //删除好友关系
    public Boolean delFriendById(Integer id1, Integer id2){
        return relationMapper.delRelationById(id1, id2);
    }

}
