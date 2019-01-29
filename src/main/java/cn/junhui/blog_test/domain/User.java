package cn.junhui.blog_test.domain;

/**
 * 军辉
 * 2019-01-29 19:03
 */
public class User {

    private Long id;

    private String aname;

    private String email;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", aname='" + aname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
