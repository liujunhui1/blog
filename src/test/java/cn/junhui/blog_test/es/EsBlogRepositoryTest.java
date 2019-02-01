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
        esBlogRepository.save(new EsBlog("Had I not seen the Sun",
                "I could have borne the shade",
                "But Light a newer Wilderness. My Wilderness has made."));
        //2
        esBlogRepository.save(new EsBlog("There is room in the halls of pleasure",
                "For a long and lordly train",
                "But one by one we must all file on, Through the narrow aisles of pain."));
        //3
        esBlogRepository.save(new EsBlog("When you are old",
                "When you are old and grey and full of sleep",
                "And nodding by the fire，take down this book."));
    }

    @Test
    public void testFindDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining() {

        Pageable pageable = PageRequest.of(0, 20);//初始化的一个分页请求

        /*
        第一次搜索：
        分别是 title 的 查询.参数 为 Sun（就是在前面的三条数据中的title中是否有Sun 这个关键词，有则拿出来），
               以此类推  --> summary 的为 is； content的为 down。。
        其中Sun匹配了第一个的 title，
        is，没有匹配，(三个数据的summary中 均未有 is 出现)
        down匹配了 第三首的 content，
        所以一共出来了两条数据，
         */
        String title = "Sun";
        String summary = "is";
        String content = "down";

        Page<EsBlog> page = esBlogRepository.findByTitleContainingOrSummaryContainingOrContentContaining(title, summary, content, pageable);

        System.out.println("--------start_1");
        for (EsBlog b : page) {
            System.out.println(b.toString());
        }
        System.out.println("************end_1");

        /*
        第二次搜索：
        三个参数都是 the
         */
        title = "the";
        summary = "the";
        content = "the";

        page = esBlogRepository.findByTitleContainingOrSummaryContainingOrContentContaining(title, summary, content, pageable);

        System.out.println("--------start_2");
        for (EsBlog b : page) {
            System.out.println(b.toString());
        }
        System.out.println("************end_2");
    }
}
