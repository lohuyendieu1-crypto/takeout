package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * 根據 openid 查詢用戶信息
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * 插入用戶信息
     * @param user
     */
    void insert(User user);

    @Select("select * from user where id = #{id}")
    User getById(Long userId);


    /**
     * 根據動態條件查詢用戶數量
     * @param map
     * @return
     */
    Integer countByMap(Map map);

}
