package cn.junhui.blog_test.controller;

import cn.junhui.blog_test.domain.Authority;
import cn.junhui.blog_test.domain.User;
import cn.junhui.blog_test.service.AuthorityService;
import cn.junhui.blog_test.service.UserService;
import cn.junhui.blog_test.util.ConstraintViolationExceptionHandler;
import cn.junhui.blog_test.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 军辉
 * 2019-01-29 19:12
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    /*
    查询所有用户
     */
    @GetMapping
    public ModelAndView list(@RequestParam(value = "async", required = false) boolean async,
                             @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                             @RequestParam(value = "name", required = false, defaultValue = "") String name,
                             Model model) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<User> page = userService.listUsersByNameLike(name, pageable);
        List<User> list = page.getContent();//当前所在页面数据列表
        System.out.println("******************");
        System.out.println(list.toString());
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        model.addAttribute("page", page);
        model.addAttribute("userList", list);
        return new ModelAndView(async == true ? "users/list :: #mainContainerRepleace" : "users/list", "userModel", model);
    }

    /*
    获取创建表单页面
     */
    @GetMapping("/add")
    public ModelAndView createForm(Model model) {
        model.addAttribute("user", new User(null, null, null));
        return new ModelAndView("users/add", "userModel", model);
    }

    /*
    获取创建表单页面
     */
    /*@PostMapping
    public ResponseEntity<Response> saveOrUpdateUser(User user) {

        try {
            userService.saveOrUpdateUser(user);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功", user));
    }*/
    @PostMapping
    public ResponseEntity<Response> saveOrUpdateUser(User user, Long authorityId) {

        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(authorityId).get());
        user.setAuthorities(authorities);

        try {
            userService.saveOrUpdateUser(user);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功", user));
    }

    /*
    删除用户
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Model model) {
        try {
            userService.removeUser(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功"));
    }

    /*
    获取修改用户的界面
     */
    @GetMapping("edit/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        model.addAttribute("user", user.get());
        return new ModelAndView("users/edit", "userModel", model);
    }

    /*@Autowired
    private UserRepository userRepository;

    *//*
    查询所有用户
    url:GET/users
     *//*
    @GetMapping
    public ModelAndView list(Model model) {
        //model.addAttribute("userList", userRepository.listUser());
        //上面是自己定义的方法，下面的是JPA提供的
        model.addAttribute("userList", userRepository.findAll());
        model.addAttribute("title", "用户管理");
        return new ModelAndView("/users/list", "userModel", model);
    }

    *//*
    根据id查询用户
     *//*
    @GetMapping("/{id}")
    public ModelAndView view(@PathVariable("id") Long id, Model model) {
        //User user = userRepository.getUserById(id);
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user.get());
        model.addAttribute("title", "查看用户");
        return new ModelAndView("users/view", "userModel", model);
    }

    *//*
    获取创建表单页面
     *//*
    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("user", new User(null, null, null));
        model.addAttribute("title", "创建用户");
        return new ModelAndView("users/form", "userModel", model);
    }

    @PostMapping
    public ModelAndView saveOrUpdateUser(User user) {
        System.out.println("新添加的user的信息：" + user);
        user = userRepository.save(user);

        return new ModelAndView("redirect:/users");
        //重定向到list.html
    }

    *//*
    删除用户
     *//*
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return new ModelAndView("redirect:/users");
    }

    *//*
    获取修改用户的界面
     *//*
    @GetMapping("/modify/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user.get());
        model.addAttribute("title", "修改用户");
        return new ModelAndView("users/form", "userModel", model);
    }*/

}
