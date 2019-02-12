package cn.junhui.blog_test.repository;

import cn.junhui.blog_test.domain.Catalog;
import cn.junhui.blog_test.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 军辉
 * 2019-02-12 19:51
 */
public interface CatalogRespository extends JpaRepository<Catalog, Long> {

    /*
    根据用户查询
     */
    List<Catalog> findByUser(User User);

    /*
    根据用户查询
     */
    List<Catalog> findByUserAndName(User user, String name);
}
