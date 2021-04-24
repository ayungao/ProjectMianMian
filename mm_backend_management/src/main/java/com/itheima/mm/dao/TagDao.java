package com.itheima.mm.dao;

import com.itheima.mm.pojo.Catalog;
import com.itheima.mm.pojo.Tag;

import java.util.List;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/16 17:54
 */
public interface TagDao {
    Long findCountByCourseId(Integer id);
    List<Tag> findTagListByCourseId(Integer CourseId);
}
