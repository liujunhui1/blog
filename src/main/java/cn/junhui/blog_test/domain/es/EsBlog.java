package cn.junhui.blog_test.domain.es;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.io.Serializable;


/**
 * 军辉
 * 2019-02-01 19:21
 * 用于 Elasticsearch 中存储博客的文档
 */
@Document(indexName = "blog", type = "blog")
public class EsBlog implements Serializable {

    /*
    serialVersionUID:简称 SUID，是当对象序列化的时候对象的一个标识
    作用：
    1.忽略SUID,相当于运行期间类的版本上的序列化和反序列上面没有差异。
    2.写一个默认的SUID,这就好像线程头部。告诉JVM所有版本中有着同样SUID的都是同一个版本。
    3.复制之前版本类的SUID。运行期间这个版本和之前版本是一样的版本。
    重点：4.使用类每个版本生成的SUID。如果SUID与新版本的类不同，
    那么运行期间两个版本是不同的，并且老版本类序列化后的实例并不可以反序列成新的类的实例。
     */
    private static final long serialVersionUID = 1L;


    @Id
    private String id;
    //在 Elasticsearch 中，要求 id 为 String型
    private String title;

    private String summary;//总结

    private String content;//内容

    protected EsBlog() {
        //JPA的规范：要求无参构造函数设为 protected 防止直接使用
    }

    public EsBlog(String title, String summary, String content) {

        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("User[id=%s,title='%s',summary='%s',content='%s']", id, title, summary, content);
    }
}
