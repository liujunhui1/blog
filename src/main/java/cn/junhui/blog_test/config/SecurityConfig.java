package cn.junhui.blog_test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.xml.ws.Action;

/**
 * 军辉
 * 2019-02-03 19:41
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*
    自定义配置类
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()//都可以访问
                .antMatchers("/users/").hasRole("ADMIN")//需要相应的角色才能访问
                .and()
                .formLogin()//基于 Form 表单登录验证
                .loginPage("/login").failureUrl("/login-error");//指明了登录路径和登陆失败路径
    }

    /*
    认证信息管理
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.inMemoryAuthentication()//认证信息存于内存中
                .passwordEncoder(new SecurityPasswordEncoder())//使用自己编写的明文密码匹配
                .withUser("junhui").password("admin").roles("ADMIN");
    }
}
