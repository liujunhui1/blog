package cn.junhui.blog_test.vo;

/**
 * 军辉
 * 2019-02-14 12:19
 * 用于在前端显示标签信息
 */
public class TagVO {
    private String name;
    private Long count;

    public TagVO() {

    }

    public TagVO(String name, Long count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TagVO{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
