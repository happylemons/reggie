package com.emilia.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.emilia.reggie.common.R;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//完善登录功能,启动类需要扫包才能生效
@WebFilter("/*") //声明本类为过滤器
public class LoginCheckFilter implements Filter {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //ServletRequest是HttpServletRequest的父类, Tomcat的底层也是Http的实现, 所以可以强制转换

        //1.强转
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //2.拿到浏览器发送请求的路径
        String requestURI = req.getRequestURI();

        //3.白名单,无需登录也可以访问的静态页面
        String[] uris = {"/backend/**", "/front/**", "/employee/login"};

        boolean match = checkUri(requestURI, uris);
        if (match) {
            chain.doFilter(req, resp);
            return;
        }

        //4.需要登录之后才可以访问,拿到登录的数据
        HttpSession session = req.getSession();
        Long employeeId = (Long) session.getAttribute("employeeId");
        if (employeeId != null) {
            //4.1已经登录,放行
            chain.doFilter(req, resp);
            return;
        }

        //4.2  没有登录
        //return R.error("NOTLOGIN");给前端没有登录信息的信号,但是过滤器返回的是void
        R r = R.error("NOTLOGIN");
        //转换成Json对象
        String jsonString = JSON.toJSONString(r);
        resp.getWriter().write(jsonString);//访问带有数据的页面时,判断是否登录, 如果没有登录的话, 跳转到登录页面

    }

    public boolean checkUri(String requestURI, String[] uris) {
        for (String uri : uris) {
            if (antPathMatcher.match(uri, requestURI)) {
                return true;
            }
        }
        return false;
    }
}


