package cn.junhui.blog_test.vo;

/**
 * 军辉
 * 2019-02-04 21:42
 */
public class Menu {
    private String name;//菜单名称
    private String url;//菜单URL

    public Menu() {
    }

    public Menu(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
