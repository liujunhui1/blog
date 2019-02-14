package cn.junhui.blog_test.controller;

import cn.junhui.blog_test.domain.User;
import cn.junhui.blog_test.domain.es.EsBlog;
import cn.junhui.blog_test.repository.es.EsBlogRepository;
import cn.junhui.blog_test.service.EsBlogService;
import cn.junhui.blog_test.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 军辉
 * 2019-02-01 20:25
 * 当控制器中存在String方法时，不能使用@RestController,否则只会返回一段字符串
 */
@Controller
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private EsBlogRepository esBlogRepository;

    @Autowired
    private EsBlogService esBlogService;

    /*@GetMapping
    public List<EsBlog> list(@RequestParam(value = "title", required = false, defaultValue = "") String title,
                             @RequestParam(value = "summary", required = false, defaultValue = "") String summary,
                             @RequestParam(value = "content", required = false, defaultValue = "") String content,
                             @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<EsBlog> page = esBlogRepository.findByTitleContainingOrSummaryContainingOrContentContaining(title, summary, content, pageable);
        return page.getContent();

    }*/

   /* @GetMapping
    public String listBlogs(@RequestParam(value = "order", required = false, defaultValue = "new") String order,
                            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        System.out.println("order:" + order + " keyword:" + keyword);
        return "redirect:/index?order=" + order + "&keyword=" + keyword;
    }*/

    @GetMapping
    public String listEsBlogs(@RequestParam(value = "order", required = false, defaultValue = "new") String order,
                              @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                              @RequestParam(value = "async", required = false) boolean async,
                              @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                              Model model) {
        Page<EsBlog> page = null;
        List<EsBlog> list = null;
        boolean isEmpty = true;//系统初始化

        try {
            if (order.equals("hot")) {//最热查询
                Sort sort = new Sort(Sort.Direction.DESC, "readSize", "commentSize", "voteSize", "createTime");
                Pageable pageable = PageRequest.of(pageIndex, pageSize);
                page = esBlogService.listHotestEsBlogs(keyword, pageable);

            } else if (order.equals("new")) {//最新查询
                Sort sort = new Sort(Sort.Direction.DESC, "createTime");
                Pageable pageable = PageRequest.of(pageIndex, pageSize);
                page = esBlogService.listNewestEsBlogs(keyword, pageable);

            }
            isEmpty = false;
        } catch (Exception e) {
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            page = esBlogService.listEsBlogs(pageable);

        }
        list = page.getContent();

        model.addAttribute("order", order);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("blogList", list);

        //首次访问页面时加载
        if (!async && !isEmpty) {
            List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
            model.addAttribute("newest", newest);

            List<EsBlog> hotest = esBlogService.listTop5HotestEsblogs();
            model.addAttribute("hotest", hotest);

            List<TagVO> tags = esBlogService.listTop30Tags();
            model.addAttribute("tags", tags);

            List<User> users = esBlogService.listTop12Users();
            model.addAttribute("users", users);

        }
        return (async == true ? "index :: #mainContainerReplace" : "index");
    }
}
