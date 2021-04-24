package com.itheima.mm.controller;

import com.itheima.framework.annotation.Controller;
import com.itheima.framework.annotation.RequestMapping;
import com.itheima.mm.constants.Constants;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.pojo.Question;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.QuestionService;
import com.itheima.mm.utils.DateUtils;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.itheima.mm.constants.Constants.QUESTION_PRE_PUBLISH;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/20 15:53
 */
@Controller
public class QuestionController {
    private QuestionService questionService = new QuestionService();

    @RequestMapping("/question/findBasicByPage")
    public void findBasicByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            QueryPageBean queryPageBean = JsonUtils.parseJSON2Object(request, QueryPageBean.class);
            PageResult pageResult = questionService.findBasicByPage(queryPageBean);
            JsonUtils.printResult(response,new Result(true,"分页查询题目列表成功",pageResult));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"分页查询题目列表失败"));
        }
    }

    @RequestMapping("/question/add")
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Question question = JsonUtils.parseJSON2Object(request, Question.class);
//            针对缺失的内容进行手动设置: status,reviewStatus,createDate,userId
            question.setStatus(Constants.QUESTION_PRE_PUBLISH);
            question.setReviewStatus(Constants.QUESTION_PRE_REVIEW);
            question.setCreateDate(DateUtils.parseDate2String(new Date()));
            User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
            question.setUserId(user.getId());

            questionService.add(question);
            JsonUtils.printResult(response,new Result(true,"添加问题成功"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"添加问题失败"));
        }
    }

}
