package com.itheima.mm.dao;

import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Course;

import java.util.List;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/15 21:21
 */
public interface CourseDao {
    void add(Course course);

    Long findTotalCount(QueryPageBean queryPageBean);

    List<Course> findPageList(QueryPageBean queryPageBean);

    void update(Course course);

    void deleteById(Integer id);

    List<Course> findAll(String status);
}
