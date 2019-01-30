package cn.junhui.blog_test.controller;

import cn.junhui.blog_test.domain.User;
import cn.junhui.blog_test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 军辉
 * 2019-01-29 19:12
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /*
    查询所有用户
    url:GET/users
     */
    @GetMapping
    public ModelAndView list(Model model) {
        model.addAttribute("userList", userRepository.listUser());
        model.addAttribute("title", "用户管理");
        return new ModelAndView("/users/list", "userModel", model);
    }

    /*
    根据id查询用户
     */
    @GetMapping("/{id}")
    public ModelAndView view(@PathVariable("id") Long id, Model model) {
        User user = userRepository.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("title", "查看用户");
        return new ModelAndView("users/view", "userModel", model);
    }

    /*
    获取创建表单页面
     */
    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "创建用户");
        return new ModelAndView("users/form", "userModel", model);
    }

    @PostMapping
    public ModelAndView saveOrUpdateUser(User user) {
        user = userRepository.saveOrUpdateUser(user);
        // System.out.println("新添加的user的信息：" + user);
        return new ModelAndView("redirect:/users");
        //重定向到list.html
    }

    /*
    删除用户
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        userRepository.deleteUserById(id);
        return new ModelAndView("redirect:/users");
    }

    /*
    获取修改用户的界面
     */
    @GetMapping("/modify/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        User user = userRepository.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("title", "修改用户");
        return new ModelAndView("users/form", "userModel", model);
    }

}
