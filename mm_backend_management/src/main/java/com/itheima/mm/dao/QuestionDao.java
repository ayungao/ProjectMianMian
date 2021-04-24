package com.itheima.mm.dao;

import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Question;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/16 17:54
 */
public interface QuestionDao {
    Long findCountByCourseId(Integer id);

    Long findTotalBasicCount(QueryPageBean queryPageBean);

    List<Question> findBasicPageList(QueryPageBean queryPageBean);

    void add(Question question);

    void addQuestionTag(Map paramMap);
}
