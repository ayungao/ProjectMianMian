package com.itheima.mm.service;

import com.alibaba.fastjson.JSONArray;
import com.itheima.mm.constants.Constants;
import com.itheima.mm.dao.QDao;
import com.itheima.mm.dao.WxMemberDao;
import com.itheima.mm.pojo.Question;
import com.itheima.mm.pojo.WxMember;
import com.itheima.mm.utils.IntegerUtils;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.*;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/29 20:53
 */
public class QService {

    public List<Map> findCategorys(Map parameterMap) throws IOException {
        Integer categoryType = null;
        if (parameterMap.get("categoryType") instanceof Integer) {
            categoryType = (Integer) parameterMap.get("categoryType");
        }else {
            categoryType = Integer.valueOf((String) parameterMap.get("categoryType"));
        }

        Integer categoryKind = null;
        if (parameterMap.get("categoryKind") instanceof Integer) {
            categoryKind = (Integer) parameterMap.get("categoryKind");
        }else {
            categoryKind = Integer.valueOf((String) parameterMap.get("categoryKind"));
        }

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        QDao qDao = sqlSession.getMapper(QDao.class);

        List<Map> categoryList = new ArrayList<>();
        if (categoryType == Constants.VIEW_QUESTION) {
            //刷题
            if (categoryKind== Constants.TAG) {
                categoryList = qDao.findCategoryListByTag(parameterMap);
            }
            if (categoryKind== Constants.COMPANY) {
                categoryList = qDao.findCategoryListByCompany(parameterMap);
            }
        }
        return categoryList;

    }

    public Map findQuestionList(Map parameterMap) throws IOException {
        Integer categoryType = null;
        if (parameterMap.get("categoryType") instanceof Integer) {
            categoryType = (Integer) parameterMap.get("categoryType");
        }else {
            categoryType = Integer.valueOf((String) parameterMap.get("categoryType"));
        }

        Integer categoryKind = null;
        if (parameterMap.get("categoryKind") instanceof Integer) {
            categoryKind = (Integer) parameterMap.get("categoryKind");
        }else {
            categoryKind = Integer.valueOf((String) parameterMap.get("categoryKind"));
        }

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        QDao qDao = sqlSession.getMapper(QDao.class);

        Map resultMap = new HashMap();
        System.out.println(parameterMap);
        if (categoryType == Constants.VIEW_QUESTION) {
            Long allCount = qDao.findAllCount(parameterMap);
            resultMap.put("allCount",allCount);
            System.out.println(allCount);

            List<Question> questionList = qDao.findQuestionListByCategoryId(parameterMap);
            resultMap.put("items",questionList);
            System.out.println(questionList);

            Integer lastID = qDao.findLastQuestionId(parameterMap);
            resultMap.put("lastID",lastID);
            System.out.println(lastID);
        }
        SqlSessionFactoryUtils.commitAndClose(sqlSession);

        return resultMap;

    }

    public void CommitAnswer(Map parameterMap) {
        SqlSession sqlSession = null;
        try {
            //1. 往tr_member_question表保存用户的做题记录: memberId、questionId、isFavorite、answerResult、tag(只有tag不在parameterMap中)
            //对于tag的分析:如果是选择题，true就是0、false就是1；如果是简单题，true就是2、false就是3
            //怎么判断是选择题，还是简答题? 看answerResult是否为空
            JSONArray jsonArray = (JSONArray) parameterMap.get("answerResult");
            Boolean answerIsRight = (Boolean) parameterMap.get("answerIsRight");
            // 第一步: 判断当前这道题是简答题还是选择题
            if (jsonArray == null) {
                //则判断answerIsRight为true或者false，设置tag的值为2或者3
                if (answerIsRight) {
                    parameterMap.put("tag", 2);
                } else {
                    parameterMap.put("tag", 3);
                }
            } else {
                //选择题，则判断answerIsRight为true或者false，设置tag的值为0或者1
                if (answerIsRight) {
                    parameterMap.put("tag", 0);
                } else {
                    parameterMap.put("tag", 1);
                }
                //parameterMap中的answerResult是一个字符串数组
                //将answerResult转换成字符串
                Object[] objects = jsonArray.toArray();
                String answerResult = Arrays.toString(objects);
                //替换map中原本的answerResult
                parameterMap.put("answerResult", answerResult);
            }
            //转换isFavorite
            Boolean isFavorite = (Boolean) parameterMap.get("isFavorite");
            if (isFavorite) {
                parameterMap.put("isFavorite", 1);
            } else {
                parameterMap.put("isFavorite", 0);
            }

            //第二步: 判断用户是否已经做过这道题了:根据memberId和questionId到tr_member_question表查询数据
            sqlSession = SqlSessionFactoryUtils.openSqlSession();
            QDao qDao = sqlSession.getMapper(QDao.class);

            Map resultMap = qDao.findByMemberIdAndQuestionId(parameterMap);

            if (resultMap != null) {
                //修改数据
                qDao.updateMemberQuestion(parameterMap);
            } else {
                //如果没有做过这道题，则新增数据
                qDao.addMemberQuestion(parameterMap);
            }

            //第三步: 保存用户的lastxxx的信息
            WxMemberDao wxMemberDao = sqlSession.getMapper(WxMemberDao.class);
            //根据id获取微信用户信息
            WxMember wxMember = wxMemberDao.findById((Integer) parameterMap.get("memberId"));
            //设置lastQuestionId
            wxMember.setLastQuestionId((Integer) parameterMap.get("id"));

            Integer categoryId = IntegerUtils.parseInteger(parameterMap.get("categoryID"));
            Integer categoryType = IntegerUtils.parseInteger(parameterMap.get("categoryType"));
            Integer categoryKind = IntegerUtils.parseInteger(parameterMap.get("categoryKind"));
            wxMember.setLastCategoryId(categoryId);
            wxMember.setLastCategoryKind(categoryKind);
            wxMember.setLastCategoryType(categoryType);
            wxMemberDao.update(wxMember);

            SqlSessionFactoryUtils.commitAndClose(sqlSession);
        } catch (Exception e) {
            e.printStackTrace();
            SqlSessionFactoryUtils.rollbackAndClose(sqlSession);
            throw new RuntimeException(e.getMessage());
        }
    }
}
