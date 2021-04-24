package com.itheima.mm.service;

import com.itheima.mm.dao.UserDao;
import com.itheima.mm.pojo.User;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/15 16:25
 */
public class UserService {

    public User login(User user) throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        //调dao方法拿到返回的user对象
        User loginUser = userDao.findByUserName(user.getUsername());
        //commit
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        //查到了有这个用户名
        if (loginUser != null) {
            //密码正确
            if (user.getPassword().equals(loginUser.getPassword())) {
                return loginUser;
            }else {
                //密码错误
                throw new RuntimeException("密码错误");
            }
        }else {
            //没查到这个用户名
            throw new RuntimeException("用户名错误");
        }



    }
}
