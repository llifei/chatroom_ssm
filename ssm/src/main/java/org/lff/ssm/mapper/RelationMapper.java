package org.lff.ssm.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RelationMapper {
    // 添加好友关系
    Boolean addRelation(@Param("id1")Integer id1, @Param("id2")Integer id2);
    // 获取好友id集合
    List<Integer> getAllFriendIds(@Param("userId") Integer userId);
    // 删除好友关系
    Boolean delRelationById(@Param("id1") Integer id1, @Param("id2") Integer id2);
}
