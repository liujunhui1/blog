package cn.junhui.blog_test.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 军辉
 * 2019-02-04 13:10
 */
@RestController
@RequestMapping("/admins")
public class AdminController {

    /*
    获取后台页面
     */
    @GetMapping
    public ModelAndView listUsers(Model model) {
        return new ModelAndView("admins/list", "menuList", model);
    }
}
