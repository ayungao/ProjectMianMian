package com.itheima.mm.dao;

import com.itheima.mm.pojo.Catalog;

import java.util.List;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/16 17:54
 */
public interface CatalogDao {
    Long findCountByCourseId(Integer id);
    List<Catalog> findCatalogListByCourseId(Integer CourseId);
}
