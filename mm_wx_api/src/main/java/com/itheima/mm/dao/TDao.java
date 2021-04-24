package com.itheima.mm.dao;

import com.itheima.mm.pojo.Tag;

import java.util.List;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/30 19:07
 */
public interface TDao {
    List<Tag> findTagListByQuestionId(Integer questionId);
}
