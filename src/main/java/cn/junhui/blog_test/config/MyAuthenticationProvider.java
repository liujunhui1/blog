package cn.junhui.blog_test.config;

import cn.junhui.blog_test.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 军辉
 * 2019-02-18 21:09
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //使用 BCrypt 加密
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("自定义Authentication处理用户和密码");


        String username = authentication.getName();//获取登录表单的用户名
        String password = (String) authentication.getCredentials();//获取登录表单的密码
        User user = (User) userDetailsService.loadUserByUsername(username);

        //PasswordEncoder encoder = new BCryptPasswordEncoder();
        //String encodePasswd = encoder.encode(password);
        String encodePasswd = passwordEncoder.encode(password);


        if (user == null) {
            System.out.println("该用户不存在");
            throw new BadCredentialsException("用户名不存在");
        }
       /* System.out.println("########MyAuthenticationProvider用户输入的账号" + username + "用户输入的密码" + password);
        System.out.println("********MyAuthenticationProvider*********用户数据库中的密码:" + user.getPassword()
                + " 加密后的输入的密码:" + encodePasswd + "密码匹配结果"
                + encodePasswd.equals(user.getPassword()) + "第二种匹配密码结果：" + passwordEncoder.matches(password, user.getPassword()));
*/

        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("密码错误");
            throw new BadCredentialsException("密码错误");
        }
        //if (password.equals(user.getPassword()));

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        // 这里直接改成retrun true;表示是支持这个执行
        return true;
    }
}
