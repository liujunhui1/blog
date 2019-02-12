package cn.junhui.blog_test.service.impl;

import cn.junhui.blog_test.domain.Catalog;
import cn.junhui.blog_test.domain.User;
import cn.junhui.blog_test.repository.CatalogRespository;
import cn.junhui.blog_test.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 军辉
 * 2019-02-12 19:59
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogRespository catalogRespository;


    @Override
    public Catalog saveCatalog(Catalog catalog) {
        //判断重复
        List<Catalog> list = catalogRespository.findByUserAndName(catalog.getUser(), catalog.getName());
        if (list != null && list.size() > 0) {
            throw new IllegalArgumentException("该分类已经存在了");
        }
        return catalogRespository.save(catalog);
    }

    @Override
    public void removeCatalog(Long id) {
        catalogRespository.deleteById(id);
    }

    @Override
    public Optional<Catalog> getCatalogById(Long id) {

        return catalogRespository.findById(id);
    }

    @Override
    public List<Catalog> listCatalogs(User user) {
        return catalogRespository.findByUser(user);
    }
}
