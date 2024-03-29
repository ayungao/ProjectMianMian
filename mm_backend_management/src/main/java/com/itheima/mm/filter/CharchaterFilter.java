package com.itheima.mm.filter; /**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/20 17:19
 */

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CharchaterFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain filterChain) throws ServletException, IOException {
        //将父接口转为子接口
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;
        //静态资源不进行过滤
        String uri = request.getRequestURI();
        if (uri.contains(".css") || uri.contains(".js") || uri.contains(".jpg") || uri.contains(".png")) {
            filterChain.doFilter(request,response);
            return;
        }
        //获取请求方法
        String method = request.getMethod();
        //解决post请求中文数据乱码问题
        if(method.equalsIgnoreCase("post")){
            request.setCharacterEncoding("utf-8");
        }
        //处理响应乱码
        response.setContentType("text/html;charset=utf-8");
        filterChain.doFilter(request,response);
    }
}
