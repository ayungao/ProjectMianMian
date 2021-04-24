package com.itheima.mm.service;

import com.itheima.mm.dao.CosDao;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/23 22:07
 */
public class CourseService {

    public List<Course> findAll() throws IOException {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CosDao cosDao = sqlSession.getMapper(CosDao.class);
        List<Course> courseList = cosDao.findAll();
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return courseList;
    }
}
