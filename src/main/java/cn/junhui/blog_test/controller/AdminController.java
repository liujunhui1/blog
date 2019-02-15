package cn.junhui.blog_test.controller;

import cn.junhui.blog_test.vo.Menu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * 军辉
 * 2019-02-04 13:10
 * 当请求进入后台管理界面时，就返回菜单列表，
 * 由于目前菜单项就一个，所以先卸载代码中，
 * 后期储存到数据库中
 */
@Controller
@RequestMapping("/admins")
public class AdminController {

    /*
    获取后台页面
     */
    @GetMapping
    public ModelAndView listUsers(Model model) {

        List<Menu> list = new ArrayList<>();
        list.add(new Menu("用户管理", "/users"));
        model.addAttribute("list", list);
        return new ModelAndView("admins/index", "model", model);
    }
}
