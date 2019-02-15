package cn.junhui.blog_test.controller;

import cn.junhui.blog_test.domain.Authority;
import cn.junhui.blog_test.domain.User;
import cn.junhui.blog_test.service.AuthorityService;
import cn.junhui.blog_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sun.plugin.javascript.navig.LinkArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 军辉
 * 2019-02-03 19:53
 */
//不能使用 @RestController
@Controller
public class MainController {

    /*
    设置了用户权限为2，系统在初始化的时候，会先初始化角色的相关信息，而这些信息是一个系统能够正常运行的必要条件
     */
    private static final Long ROLE_USER_AUTHORITY_ID = 2L;//用户权限（博主）

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;


    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }


    @GetMapping("/login-error")
    public String LoginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "登录失败，用户账号或密码错误");
        return "login";
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public String registerUser(User user) {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID).get());
        user.setAuthorities(authorities);
        userService.registerUser(user);
        return "redirect:/login";
    }
}
