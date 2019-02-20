package cn.junhui.blog_test.controller;

import cn.junhui.blog_test.domain.Blog;
import cn.junhui.blog_test.domain.Catalog;
import cn.junhui.blog_test.domain.User;
import cn.junhui.blog_test.domain.Vote;
import cn.junhui.blog_test.service.BlogService;
import cn.junhui.blog_test.service.CatalogService;
import cn.junhui.blog_test.service.UserService;
import cn.junhui.blog_test.util.ConstraintViolationExceptionHandler;
import cn.junhui.blog_test.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

/**
 * 军辉
 * 2019-02-04 12:36
 * 处理用户空间相关的控制
 */
@Controller
@RequestMapping("/u")
public class UserspaceController {

    @Autowired
    private UserService userService;

    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CatalogService catalogService;

    @Value("${file.server.url}")
    private String fileServerUrl;

    /*
    用户主页
     */
    @GetMapping("/{username}")
    public String userSpace(@PathVariable("username") String username, Model model) {
        System.out.println("username:" + username);
        User user = (User) userDetailsService.loadUserByUsername(username);
        // System.out.println("用户主页的当前用户是:" + user);
        model.addAttribute("user", user);
        return "redirect:/u/" + username + "/blogs";
    }


    /*
    获取用户的博客列表
     */
    @GetMapping("/{username}/blogs")
    public String listBlogsByOrder(@PathVariable("username") String username,
                                   @RequestParam(value = "order", required = false, defaultValue = "new") String order,
                                   @RequestParam(value = "catalog", required = false) Long catalogId,
                                   @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                   @RequestParam(value = "async", required = false) boolean async,
                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                   Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        Page<Blog> page = null;

        if (null != catalogId && catalogId > 0) {//分类查询
            //TODO
            Catalog catalog = catalogService.getCatalogById(catalogId).get();
            //下面的一行代码做了修改(方法过时了)
            // Pageable pageable =new PageRequest(pageIndex, pageSize);
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            page = blogService.listBlogsByCatalog(catalog, pageable);
            order = "";
        } else if (order.equals("hot")) {//最热查询
            Sort sort = new Sort(Sort.Direction.DESC, "readSize", "commentSize", "voteSize");
            Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
        } else if (order.equals("new")) {//最新查询
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            page = blogService.listBlogsByTitleVote(user, keyword, pageable);
        }
        List<Blog> list = page.getContent(); //当前所在页面数据列表
        model.addAttribute("user", user);
        model.addAttribute("order", order);
        model.addAttribute("catalogId", catalogId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("blogList", list);
        model.addAttribute("catalogId", catalogId);

        return (async == true ? "/userspace/u :: #mainContainerRepleace" : "/userspace/u");

    }

    /*
    获取博客展示页面
     */
    @GetMapping("/{username}/blogs/{id}")
    public String GetBlogById(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        User principal = null;
        //每次读取，简单的可以认为阅读量增加1次
        Optional<Blog> blog = blogService.getBlogById(id);

        //判断操作用户是否为博客的所有者
        boolean isBlogOwner = false;
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && username.equals(principal.getUsername())) {
                isBlogOwner = true;
            }
        }

        //判断操作用户点赞情况
        List<Vote> votes = blog.get().getVotes();
        Vote currentVotes = null;//当前用户点赞用户

        if (principal != null) {
            for (Vote v : votes) {
                if (v.getUser().getUsername().equals(principal.getUsername())) {
                    currentVotes = v;
                    break;
                }
            }
        }

        System.out.println("当前页面用户信息:" + principal);
        model.addAttribute("user", principal);
        model.addAttribute("currentVotes", currentVotes);
        model.addAttribute("isBlogOwner", isBlogOwner);
        model.addAttribute("blogModel", blog.get());

        return "/userspace/blog";
    }

    /*
    获取新增博客的界面
     */
    @GetMapping("/{username}/blogs/edit")
    public ModelAndView createBlog(@PathVariable("username") String username, Model model) {

        //获取用户分类列表
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);

        model.addAttribute("catalogs", catalogs);
        model.addAttribute("blog", new Blog(null, null, null));
        model.addAttribute("fileServerUrl", fileServerUrl);//文件服务器的地址返回给客户端

        return new ModelAndView("/userspace/blogedit", "blogModel", model);
    }

    /*
    获取编辑博客的界面
     */
    @GetMapping("/{username}/blogs/edit/{id}")
    public ModelAndView editBlog(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {

        //获取用户分类列表
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);

        model.addAttribute("catalogs", catalogs);
        model.addAttribute("blog", blogService.getBlogById(id).get());
        model.addAttribute("fileServerUrl", fileServerUrl);//文件服务器的地址返回给客户端

        return new ModelAndView("/userspace/blogedit", "blogModel", model);
    }

    /*
    保存博客
     */
    @PostMapping("/{username}/blogs/edit")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {

        //对 Catalog 进行空处理
        if (blog.getCatalog().getId() == null) {
            return ResponseEntity.ok().body(new Response(false, "分选择分类"));
        }
        try {
            //判断修改还是新增
            if (blog.getId() != null) {
                Optional<Blog> optionalBlog = blogService.getBlogById(blog.getId());
                if (optionalBlog.isPresent()) {
                    Blog orignalBlog = optionalBlog.get();
                    orignalBlog.setTitle(blog.getTitle());
                    orignalBlog.setContent(blog.getContent());
                    orignalBlog.setSummary(blog.getSummary());
                    orignalBlog.setCatalog(blog.getCatalog());
                    orignalBlog.setTags(blog.getTags());
                    blogService.saveBlog(blog);
                }
            } else {
                User user = (User) userDetailsService.loadUserByUsername(username);
                blog.setUser(user);
                blogService.saveBlog(blog);
            }
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
        return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));

    }

    /*
    删除博客
     */
    @DeleteMapping("/{username}/blogs/{id}")
    //@PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> deleteBlog(@PathVariable("username") String username, @PathVariable("id") Long id) {
        try {
            blogService.removeBlog(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        String redirectUrl = "/u/" + username + "/blogs";
        return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
    }

    /*
    获取个人设置页面
     */
    @GetMapping("/{username}/profile")
    //@PreAuthorize("authenticationConfiguration.name.equals(#username)")//这个注解的意思是：只有用户自己才可以修改自己的个人资料
    public ModelAndView profile(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        System.out.println("个人设置的当前用户是:" + user);
        model.addAttribute("fileServerUrl", fileServerUrl);//文件服务器的地址返回给客户端
        return new ModelAndView("/userspace/profile", "userModel", model);
    }

    /*
    保存个人设置
     */
    @PostMapping("/{username}/profile")
    //@PreAuthorize("authenticationConfiguration.name.equales(#username)")
    public String saveProfile(@PathVariable("username") String username, User user) {
        User originalUser = userService.getUserById(user.getId()).get();
        originalUser.setEmail(user.getEmail());
        originalUser.setName(user.getName());

        //判断密码是否做了更改
        String rawPassword = originalUser.getPassword();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePasswd = encoder.encode(user.getPassword());
        boolean isMatch = encoder.matches(rawPassword, encodePasswd);
        if (!isMatch) {
            originalUser.setEncodePassword(user.getPassword());
        }
        userService.saveOrUpdateUser(user);
        return "redirect:/u/" + username + "/profile";
    }

    /*
    获取编辑个人头像的界面
     */
    @GetMapping("/{username}/avatar")
    //@PreAuthorize("authenticationConfiguration.name.equals(#username)")
    public ModelAndView avatar(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return new ModelAndView("/userspace/avatar", "userModel", model);
    }

    /*
    保存头像
     */
    @PostMapping("/{username}/avatar")
    //@PreAuthorize("authenticationConfiguration.name.equals(#username)")
    public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
        String avatarUrl = user.getAvatar();

        User originalUser = userService.getUserById(user.getId()).get();
        originalUser.setAvatar(avatarUrl);
        userService.saveOrUpdateUser(originalUser);

        return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));
    }
}
