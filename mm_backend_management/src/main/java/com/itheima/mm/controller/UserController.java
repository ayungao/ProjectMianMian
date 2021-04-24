package com.itheima.mm.controller;

import com.itheima.framework.annotation.Controller;
import com.itheima.framework.annotation.RequestMapping;
import com.itheima.mm.constants.Constants;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.UserService;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/15 16:02
 */
@Controller
public class UserController {
    private UserService userService = new UserService();

    @RequestMapping("/user/login")
    public void userLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数封装到User类中
        User user = JsonUtils.parseJSON2Object(request, User.class);
        try {
            if (user != null) {
                //调用userService的方法，返回查到的user对象
                User loginUser = userService.login(user);
                //把返回的user对象存到session中
                request.getSession().setAttribute(Constants.LOGIN_USER,loginUser);
                //成功：封装后转json返回
                JsonUtils.printResult(response,new Result(true,"登录成功",loginUser));
            }
        } catch (Exception e) {
            //返回失败信息
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,e.getMessage(),null));
        }

    }

    @RequestMapping("/user/logout")
    public void userLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //销毁session
        request.getSession().invalidate();
//        返回结果
        JsonUtils.printResult(response,new Result(true,"退出成功",null));
    }
}
