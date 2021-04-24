package com.itheima.mm.dao;

import com.itheima.mm.pojo.Course;

import java.util.List;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/23 22:14
 */
public interface CosDao {
    List<Course> findAll();
}
