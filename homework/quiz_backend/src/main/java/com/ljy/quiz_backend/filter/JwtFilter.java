package com.ljy.quiz_backend.filter;

import com.alibaba.fastjson.JSONObject;
import com.ljy.quiz_backend.model.Result;
import com.ljy.quiz_backend.utils.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class JwtFilter implements Filter {

    //初始化，只调用一次；
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("JwtFilter init");
    }



    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("JwtFilter拦截到请求");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if ("OPTIONS".equals(request.getMethod())) {
            chain.doFilter(req, res);
            return;
        }

        //1、获取请求url;
        String url=request.getRequestURL().toString();
        //2、判断url中是否包含login,如果包含，则说明是登录操作，放行；
        if(url.contains("login")){
            chain.doFilter(req, res);
            return;
        }

        //3、获取请求头中的令牌(token)
        String token = request.getHeader("token");
        //4、判断令牌是否存在，如果不存在，返回未登录信息。
        if(!StringUtils.hasLength(token)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 设置状态码为 401
            Result result = Result.error("NOT_LOGIN");
            //手动将对象转为json，并传回前端；
            String noLogin= JSONObject.toJSONString(result);
            response.setContentType("application/json;charset=UTF-8");
            res.getWriter().write(noLogin);
            return;
        }
        //5、解析token，如果解析失败，返回未登录信息。
        try {
            JwtUtil.parseToken(token);//只要解析不成功，就说明有问题；
        }catch(Exception e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Result result = Result.error("NOT_LOGIN");
            //手动将对象转为json，并传回前端；
            String noLogin=JSONObject.toJSONString(result);
            response.setContentType("application/json;charset=UTF-8");
            res.getWriter().write(noLogin);
            return;
        }


        chain.doFilter(req, res);
    }

    //销毁，只调用一次；
    public void destroy() {
        System.out.println("JwtFilter destroy");
    }
}
