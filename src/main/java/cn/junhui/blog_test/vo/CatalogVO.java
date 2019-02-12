package cn.junhui.blog_test.vo;

import cn.junhui.blog_test.domain.Catalog;

/**
 * 军辉
 * 2019-02-12 19:32
 * <p>
 * 前台调用后台的传递用的参数对象
 */
public class CatalogVO {

    private String username;
    private Catalog catalog;

    public CatalogVO() {

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public String toString() {
        return "CatalogVO{" +
                "username='" + username + '\'' +
                ", catalog=" + catalog +
                '}';
    }
}
