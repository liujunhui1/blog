package cn.junhui.blog_test.controller;

import cn.junhui.blog_test.domain.User;
import cn.junhui.blog_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 军辉
 * 2019-02-03 19:53
 */
//不能使用 @RestController
@Controller
public class MainController {
    @Autowired
    private UserService userService;


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


    @GetMapping("/login-error")
    public String LoginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("errMsg", "登录失败，用户账号或密码错误");
        return "login";
    }

    @PostMapping("/register")
    public String registerUser(User user) {
        userService.registerUser(user);
        return "redirect:/login";
    }
}
