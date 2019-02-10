package cn.junhui.blog_test.service;

import cn.junhui.blog_test.domain.Blog;
import cn.junhui.blog_test.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * 军辉
 * 2019-02-09 12:40
 */
public interface BlogService {

    Blog saveBlog(Blog blog);

    void removeBlog(Long id);

    Optional<Blog> getBlogById(Long id);

    /*
    根据用户进行博客名称分页模糊查询（最新）
     */
    Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable);

    /*
    根据用户进行博客名称分页模糊查询（最热）
     */
    Page<Blog> listBlogsBtTitleVoteAndSort(User user, String title, Pageable pageable);

    /*
    阅读量递增
     */
    void readingIncrease(Long id);
}
