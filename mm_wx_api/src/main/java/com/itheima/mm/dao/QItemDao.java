package com.itheima.mm.dao;

import com.itheima.mm.pojo.QuestionItem;

import java.util.List;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/30 19:08
 */
public interface QItemDao {
    List<QuestionItem> findItemListByQuestionId(Integer questionId);
}
