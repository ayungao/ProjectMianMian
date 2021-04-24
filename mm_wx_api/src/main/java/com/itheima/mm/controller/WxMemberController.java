package com.itheima.mm.controller;

import com.itheima.framework.annotation.Controller;
import com.itheima.framework.annotation.RequestMapping;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.WxMember;
import com.itheima.mm.service.WxMemberService;
import com.itheima.mm.utils.DateUtils;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/23 22:32
 */
@Controller
public class WxMemberController {
    private WxMemberService wxMemberService = new WxMemberService();
    @RequestMapping("/wxMember/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            WxMember wxMember = JsonUtils.parseJSON2Object(request, WxMember.class);
            WxMember loginMember = wxMemberService.findByNickname(wxMember.getNickName());
            if (loginMember==null) {
                //没注册过
                wxMember.setCreateTime(DateUtils.parseDate2String(new Date()));
                wxMemberService.addWxMember(wxMember);
            }else {
                //注册过
                wxMember = loginMember;
            }

            Map resultMap = new HashMap();
            resultMap.put("token",wxMember.getId());
            resultMap.put("userInfo",wxMember);

            JsonUtils.printResult(response,new Result(true,"登陆成功",resultMap));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"登陆失败"));
        }
    }

    @RequestMapping("/wxMember/setCityCourse")
    public void setCityCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //获取请求参数
            Map parameterMap = JsonUtils.parseJSON2Object(request, Map.class);
            //获取请求头中的用户id
            String authorization = request.getHeader("Authorization");
            Integer id = Integer.valueOf(authorization.substring(7));
            //通过id获取用户
            WxMember wxMember = wxMemberService.findById(id);

//      判断请求参数cityID和subjectID的类型并转换成integer
            Integer cityId = null;
            if (parameterMap.get("cityID") instanceof Integer) {
                cityId = (Integer) parameterMap.get("cityID");
            }else {
                cityId = Integer.valueOf((String) parameterMap.get("cityID"));
            }

            Integer courseId = null;
            if (parameterMap.get("subjectID") instanceof Integer) {
                courseId = (Integer) parameterMap.get("subjectID");
            }else {
                courseId = Integer.valueOf((String) parameterMap.get("subjectID"));
            }

            wxMember.setCityId(cityId);
            wxMember.setCourseId(courseId);
            wxMemberService.update(wxMember);
            JsonUtils.printResult(response,new Result(true,"设置城市和学科ID成功"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(true,"设置城市和学科ID失败"));
        }


    }

    @RequestMapping("/wxMember/center")
    public void center(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //获取请求头中的用户id
            String authorization = request.getHeader("Authorization");
            Integer memberId = Integer.valueOf(authorization.substring(7));

//        根据用户的id查询到用户信息
            WxMember wxMember = wxMemberService.findById(memberId);
            //3. 调用业务层的方法，查询到响应数据
            Map resultMap = wxMemberService.findCenterInfo(wxMember);
            JsonUtils.printResult(response,new Result(true,"获取个人中心信息成功",resultMap));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"获取个人中心信息失败"));

        }
    }
}
