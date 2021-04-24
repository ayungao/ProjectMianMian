package com.itheima.mm.controller;

import com.itheima.framework.annotation.Controller;
import com.itheima.framework.annotation.RequestMapping;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.service.CityService;
import com.itheima.mm.service.CourseService;
import com.itheima.mm.utils.JsonUtils;
import com.itheima.mm.utils.LocationUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/23 21:07
 */
@Controller
public class CommonController {
    private CityService cityService = new CityService();
    private CourseService courseService = new CourseService();
    @RequestMapping("/common/citys")
    public void cities(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Map parameterMap = JsonUtils.parseJSON2Object(request, Map.class);
            Map resrultMap = cityService.findCityList(parameterMap);
            JsonUtils.printResult(response,new Result(true,"定位成功",resrultMap));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"定位失败"));
        }
    }

    @RequestMapping("/common/courseList")
    public void courseList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<Course> courseList = courseService.findAll();
            JsonUtils.printResult(response,new Result(true,"查询学科列表成功",courseList));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"查询学科列表失败"));
        }
    }
}
