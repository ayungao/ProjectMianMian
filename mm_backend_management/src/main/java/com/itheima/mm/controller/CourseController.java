package com.itheima.mm.controller;

import com.itheima.framework.annotation.Controller;
import com.itheima.framework.annotation.RequestMapping;
import com.itheima.mm.constants.Constants;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.CourseService;
import com.itheima.mm.utils.DateUtils;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/15 20:53
 */
@Controller
public class CourseController {
    private CourseService courseService = new CourseService();

    @RequestMapping("/course/add")
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //获取参数name和isShow
            Course course = JsonUtils.parseJSON2Object(request, Course.class);
            //设置参数CreateDate
            course.setCreateDate(DateUtils.parseDate2String(new Date()));
            //设置参数userId
            User user = (User) request.getSession().getAttribute(Constants.LOGIN_USER);
            course.setUserId(user.getId());
            //设置参数orderNo
            course.setOrderNo(1);
            //调业务层方法添加
            courseService.add(course);
            JsonUtils.printResult(response,new Result(true,"添加成功"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"添加失败"));
        }
    }

    @RequestMapping("/course/findByPage")
    public void findByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //获取参数封装到queryPageBean
            QueryPageBean queryPageBean = JsonUtils.parseJSON2Object(request, QueryPageBean.class);
            //调业务层方法返回pageResult
            PageResult pageResult = courseService.findByPage(queryPageBean);
            JsonUtils.printResult(response,new Result(true,"查询成功",pageResult));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"查询失败"));
        }
    }

    @RequestMapping("/course/update")
    public void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Course course = JsonUtils.parseJSON2Object(request, Course.class);
            //调业务层方法更新course
            courseService.update(course);
            JsonUtils.printResult(response,new Result(true,"更新成功"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"更新失败"));
        }
    }

    @RequestMapping("/course/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Integer id = Integer.valueOf(request.getParameter("id"));
            courseService.deleteById(id);
            JsonUtils.printResult(response,new Result(true,"删除成功"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,e.getMessage()));
        }
    }

    @RequestMapping("/course/findAll")
    public void findBasicByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得请求参数题目状态（status）
        try {
            String status = request.getParameter("status");
            List<Course> courseList = courseService.findAll(status);
            JsonUtils.printResult(response,new Result(true,"成功获取学科列表",courseList));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"获取学科列表失败"));
        }

    }
}
