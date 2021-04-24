package com.itheima.mm.service;

import com.itheima.mm.dao.QuestionDao;
import com.itheima.mm.dao.QuestionItemDao;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Question;
import com.itheima.mm.pojo.QuestionItem;
import com.itheima.mm.pojo.Tag;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/20 15:56
 */
public class QuestionService {

    public PageResult findBasicByPage(QueryPageBean queryPageBean) throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        QuestionDao questionDao = sqlSession.getMapper(QuestionDao.class);

        Long total = questionDao.findTotalBasicCount(queryPageBean);
        List<Question> basicList = questionDao.findBasicPageList(queryPageBean);

        SqlSessionFactoryUtils.commitAndClose(sqlSession);

        return new PageResult(total, basicList);

    }

    public void add(Question question) throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        QuestionDao questionDao = sqlSession.getMapper(QuestionDao.class);

        questionDao.add(question);

        //判断选项的集合是否为空，如果不为空将题目的选项信息，存储到t_question_item表
        QuestionItemDao questionItemDao = sqlSession.getMapper(QuestionItemDao.class);
        List<QuestionItem> questionItemList = question.getQuestionItemList();
        if (questionItemList != null && questionItemList.size() > 0) {
            for (QuestionItem questionItem : questionItemList) {
                questionItem.setQuestionId(question.getId());
                questionItemDao.add(questionItem);
            }
        }

        //将题目和标签的关联信息，存储到tr_question_tag表中
        //获取该题目的所有标签
        List<Tag> tagList = question.getTagList();
        if (tagList != null && tagList.size() > 0) {
            for (Tag tag : tagList) {
                Map paramMap = new HashMap<>();
                paramMap.put("questionId",question.getId());
                paramMap.put("tagId",tag.getId());
                //进行关联
                questionDao.addQuestionTag(paramMap);
            }
        }

        SqlSessionFactoryUtils.commitAndClose(sqlSession);
    }
}
