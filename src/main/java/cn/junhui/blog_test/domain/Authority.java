package cn.junhui.blog_test.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * 军辉
 * 2019-02-05 18:05
 * 该实体代表权限，也等同于角色
 */
@Entity
public class Authority implements GrantedAuthority {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自增长策略
    private Long id;

    @Column(nullable = false)
    private String name;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
