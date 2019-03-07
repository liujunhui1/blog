package cn.junhui.blog_test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 军辉
 * 2019-02-03 19:41
 *
 * @EnableGlobalMethodSecurity(prePostEnabled = true) 启用方法级别的安全设置
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //设置用于识别“记住我”身份验证而创建的令牌的键
    private static final String KEY = "junhui";

    //认证信息是从数据库中来获取的
    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    /* @Autowired
     private PasswordEncoder passwordEncoder;
 */
    @Autowired
    private AuthenticationProvider provider;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    /*
    记住我 功能
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //自动创建数据库表，使用一次后注释掉，不然会报错
//        jdbcTokenRepository.setCreateTableOnStartup(true);

        return tokenRepository;
    }

 /*   @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //使用 BCrypt 加密
    }*/

    /*@Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        System.out.println("AuthenticationProvider 经过加密的密码是" + passwordEncoder);
        //设置密码加密方式
        return authenticationProvider;
    }*/


    /*
     *  自定义配置类
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/static/**", "/css/**", "/js/**", "/fonts/**", "/images/**", "/index","/file/test1").permitAll()//都可以访问
                //.antMatchers("/h2-console/**").permitAll()
                .antMatchers("/login", "/register").permitAll()
                .antMatchers("/admins/**").hasRole("ADMIN")//需要相应的角色才能访问
                .anyRequest().authenticated()//任何尚未匹配的url只需要验证用户即可访问
                .and()
                .formLogin()//基于 Form 表单登录验证
                //指明了登录路径和登录失败路径,并且接受了post请求的login方法，处理方法为下面的configureGlobal方法
                //loginProcessingUrl()指明登录处理路径
                .loginPage("/login").failureUrl("/login-error").successForwardUrl("/")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .permitAll()
                .and()
                .rememberMe() //启用 remember-me
                .rememberMeParameter("remember-me").userDetailsService(userDetailsService)
                .tokenRepository(persistentTokenRepository())// 设置TokenRepository
                //.tokenValiditySeconds(600)// 配置Cookie过期时间
                .and().exceptionHandling().accessDeniedPage("/403");//处理异常，拒绝访问就重定向到403页面

        //禁用H2控制台的CSRF防护
        //http.csrf().ignoringAntMatchers("/h2-console/**");

        //允许来自同一来源的H2控制台的请求
        //http.headers().frameOptions().sameOrigin();


    }


    /*  认证信息管理
    自定义用户名和密码
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder managerBuilder) throws Exception {
        /*managerBuilder.inMemoryAuthentication()//认证信息存于内存中
                .passwordEncoder(new SecurityPasswordEncoder())//使用自己编写的明文密码匹配
                .withUser("junhui").password("admin").roles("ADMIN");*/
        //managerBuilder.userDetailsService(userDetailsService);

        managerBuilder.authenticationProvider(provider);
    }


}
