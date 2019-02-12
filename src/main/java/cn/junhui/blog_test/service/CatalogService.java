package cn.junhui.blog_test.service;

import cn.junhui.blog_test.domain.Catalog;
import cn.junhui.blog_test.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * 军辉
 * 2019-02-12 19:56
 */
public interface CatalogService {


    Catalog saveCatalog(Catalog catalog);

    void removeCatalog(Long id);

    Optional<Catalog> getCatalogById(Long id);

    /*
    获取分类列表
     */
    List<Catalog> listCatalogs(User user);
}
