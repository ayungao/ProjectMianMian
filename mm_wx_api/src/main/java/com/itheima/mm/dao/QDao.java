package com.itheima.mm.dao;

import com.itheima.mm.pojo.Question;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/29 20:54
 */
public interface QDao {
    List<Map> findCategoryListByTag(Map parameterMap);

    List<Map> findCategoryListByCompany(Map parameterMap);



    List<Question> findQuestionListByCategoryId(Map parameterMap);

    Integer findLastQuestionId(Map parameterMap);

    Long findAllCount(Map parameterMap);

    Map findByMemberIdAndQuestionId(Map parameterMap);

    void updateMemberQuestion(Map parameterMap);

    void addMemberQuestion(Map parameterMap);
}
