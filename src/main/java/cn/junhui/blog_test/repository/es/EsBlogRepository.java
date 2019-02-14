package cn.junhui.blog_test.repository.es;

import cn.junhui.blog_test.domain.es.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 军辉
 * 2019-02-01 19:30
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String> {
    /*
    分页查询博客
    containing:包含
    模糊查询的方法名比较冗长，Spring Data会根据方法签名，自动猜测这个查询语句的意图。从而实现查询数据的目的，
    这个方法是用户模糊查询标题，摘要，正文，抱歉包含有搜索关键字的数据
     */
    Page<EsBlog> findByTitleContainingOrSummaryContainingOrContentContaining(String title, String summary, String content, String tags, Pageable pageable);

    /*
    根据 Blog 的 id 查询 EsBlog
     */
    EsBlog findByBlogId(Long blogId);
}
