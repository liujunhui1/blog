package cn.junhui.blog_test.es;

import cn.junhui.blog_test.domain.es.EsBlog;
import cn.junhui.blog_test.repository.es.EsBlogRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 军辉
 * 2019-02-01 19:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsBlogRepositoryTest {

    @Autowired
    private EsBlogRepository esBlogRepository;

    @Before
    public void initRepositoryData() {
        //清楚所有数据
        esBlogRepository.deleteAll();

        //初始化数据(三个数据)
        //1
        esBlogRepository.save(new EsBlog("Q W E R T title",
                "Y U I O P summary",
                "A S D F content"));
        //2
        esBlogRepository.save(new EsBlog("G H J K L title",
                "Z X C V summary test",
                "B N M content"));
        //3
        esBlogRepository.save(new EsBlog("1 2 3 title",
                " 4 5 6 summary test",
                "7 8 9 content"));
    }

    @Test
    public void testFindDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining() {

        Pageable pageable = PageRequest.of(0, 20);//初始化的一个分页请求

        /*
        第一次搜索：
        Q Z what 作为了查询参数
        Q 匹配了第一个，Z匹配了第二个，what 未匹配到
        所以一共出来了两条数据，
         */
        String title = "Q";
        String summary = "Z";
        String content = "what";

        Page<EsBlog> page = esBlogRepository.findByTitleContainingOrSummaryContainingOrContentContaining(title, summary, content, pageable);

        System.out.println("--------start_1");
        for (EsBlog b : page) {
            System.out.println(b.toString());
        }
        System.out.println("************end_1");

        /*
        第二次搜索：
        只使用了一个 title，匹配了三个，
        所以出来了三条数据
         */
        title = "A";
        summary = "";
        content = "";

        page = esBlogRepository.findByTitleContainingOrSummaryContainingOrContentContaining(title, summary, content, pageable);

        System.out.println("--------start_2");
        for (EsBlog b : page) {
            System.out.println(b.toString());
        }
        System.out.println("************end_2");
    }
}
