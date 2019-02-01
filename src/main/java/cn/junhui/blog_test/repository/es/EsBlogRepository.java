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
     */
    Page<EsBlog> findByTitleContainingOrSummaryContainingOrContentContaining(String title, String summary, String content, Pageable pageable);
}
