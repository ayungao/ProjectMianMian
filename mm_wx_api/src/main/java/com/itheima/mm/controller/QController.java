package com.itheima.mm.controller;

import com.itheima.framework.annotation.Controller;
import com.itheima.framework.annotation.RequestMapping;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.WxMember;
import com.itheima.mm.service.QService;
import com.itheima.mm.service.WxMemberService;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/29 20:52
 */
@Controller
public class QController {
    private QService qService = new QService();
    private WxMemberService wxMemberService = new WxMemberService();

    @RequestMapping("/question/categorys")
    public void categorys(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //获取请求参数
            Map parameterMap = JsonUtils.parseJSON2Object(request, Map.class);
            //获取请求头中的用户id
            String authorization = request.getHeader("Authorization");
            Integer id = Integer.valueOf(authorization.substring(7));
            //通过id获取用户
            WxMember wxMember = wxMemberService.findById(id);

            parameterMap.put("memberId",wxMember.getId());
            parameterMap.put("cityId",wxMember.getCityId());
            parameterMap.put("courseId",wxMember.getCourseId());

            List<Map> categoryList = qService.findCategorys(parameterMap);
            JsonUtils.printResult(response,new Result(true,"获取题目分类成功",categoryList));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"获取题目分类失败"));
        }
    }

    @RequestMapping("/question/questions")
    public void questions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //获取请求头中的用户id
            String authorization = request.getHeader("Authorization");
            Integer id = Integer.valueOf(authorization.substring(7));

            //获取请求参数
            Map parameterMap = JsonUtils.parseJSON2Object(request, Map.class);

            parameterMap.put("memberId",id);
            System.out.println(parameterMap);
            Map resultMap = qService.findQuestionList(parameterMap);

            JsonUtils.printResult(response,new Result(true,"获取题目列表成功",resultMap));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"获取题目列表失败"));
        }
    }

    @RequestMapping("/question/commit")
    public void CommitAnswer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //获取请求头中的用户id
            String authorization = request.getHeader("Authorization");
            Integer id = Integer.valueOf(authorization.substring(7));

            //获取请求参数
            Map parameterMap = JsonUtils.parseJSON2Object(request, Map.class);

            parameterMap.put("memberId",id);
            qService.CommitAnswer(parameterMap);
            JsonUtils.printResult(response,new Result(true,"答案提交成功"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"答案提交失败"));
        }

    }
}
