package cn.junhui.blog_test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * 军辉
 * 2019-02-03 19:41
 *
 * @EnableGlobalMethodSecurity(prePostEnabled = true) 启用方法级别的安全设置
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //设置用于识别“记住我”身份验证而创建的令牌的键
    private static final String KEY = "junhui";

    //认证信息是从数据库中来获取的
    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //使用 BCrypt 加密
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        //设置密码加密方式
        return authenticationProvider;
    }



    /*
    自定义配置类
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()//都可以访问
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/admins/").hasRole("ADMIN")//需要相应的角色才能访问
                .and()
                .formLogin()//基于 Form 表单登录验证
                //指明了登录路径和登录失败路径,并且接受了post请求的login方法，处理方法为下面的configureGlobal方法
                .loginPage("/login").failureUrl("/login-error")
                .and().rememberMe().key(KEY) //启用 remember me
                .and().exceptionHandling().accessDeniedPage("/403");//处理异常，拒绝访问就重定向到403页面

        //禁用H2控制台的CSRF防护
        http.csrf().ignoringAntMatchers("/h2-console/**");

        //允许来自同一来源的H2控制台的请求
        http.headers().frameOptions().sameOrigin();
    }

    /*
    认证信息管理
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.inMemoryAuthentication()//认证信息存于内存中
                .passwordEncoder(new SecurityPasswordEncoder())//使用自己编写的明文密码匹配
                .withUser("junhui").password("admin").roles("ADMIN");
       /* managerBuilder.userDetailsService(userDetailsService);
        managerBuilder.authenticationProvider(authenticationProvider());*/
    }
}
