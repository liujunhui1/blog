package cn.junhui.blog_test.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 军辉
 * 2019-02-19 12:27
 * 自定义的登录成功的逻辑
 */
@Component("MyAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        //什么都不做的话，那就直接调用父类的方法
        super.onAuthenticationSuccess(request, response, authentication);

        //这里可以根据实际情况，来确定跳转到页面或json格式
        //如果是返回json格式，
        Map<String, String> map = new HashMap<>();
        map.put("code", "200");
        map.put("msg", "登录成功");
        response.setContentType("application/json;charest=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(map));


        //如果是要跳转到某个页面，比如： whoim
        //new DefaultRedirectStrategy().sendRedirect(request, response, "/whoim");

    }
}
