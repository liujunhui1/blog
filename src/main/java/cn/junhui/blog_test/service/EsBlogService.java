package cn.junhui.blog_test.service;

import cn.junhui.blog_test.domain.User;
import cn.junhui.blog_test.domain.es.EsBlog;
import cn.junhui.blog_test.vo.TagVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 军辉
 * 2019-02-14 12:21
 */
public interface EsBlogService {

    void removeEsBlog(String id);

    EsBlog updateEsBlog(EsBlog esBlog);

    EsBlog getEsBlogByBlogId(Long blogId);

    /*
    最新博客列表
     */
    Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable);

    /*
    最热博客列表，分页
     */
    Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable);

    /*
    博客列表，分页
     */
    Page<EsBlog> listEsBlogs(Pageable pageable);

    /*
    最新前五
     */
    List<EsBlog> listTop5NewestEsBlogs();

    /*
    最热前五
     */
    List<EsBlog> listTop5HotestEsblogs();

    /*
    最热前30标签
     */
    List<TagVO> listTop30Tags();

    /*
    最热前12用户
     */
    List<User> listTop12Users();

}
