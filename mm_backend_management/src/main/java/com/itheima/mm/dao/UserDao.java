package com.itheima.mm.dao;

import com.itheima.mm.pojo.User;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/15 16:27
 */
public interface UserDao {
    //登录方法
    User findByUserName(String username);
}
