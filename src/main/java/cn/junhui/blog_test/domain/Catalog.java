package cn.junhui.blog_test.domain;

import cn.junhui.blog_test.domain.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 军辉
 * 2019-02-12 18:53
 * <p>
 * 分类
 */
@Entity
public class Catalog implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    private Long id;

    private String name;

    private User user;

    protected Catalog() {
    }

    public Catalog(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user=" + user +
                '}';
    }
}
