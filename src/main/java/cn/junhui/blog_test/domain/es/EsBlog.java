package cn.junhui.blog_test.domain.es;

import cn.junhui.blog_test.domain.Blog;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 军辉
 * 2019-02-01 19:21
 * 文档类
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

    @Field(type = FieldType.Long)
    private Long blogId;//Blog实体的id

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String summary;//总结

    @Field(type = FieldType.Text)
    private String content;//内容

    @Field(type = FieldType.Keyword, index = false)//不做全文检索字段
    private String username;

    @Field(type = FieldType.Keyword, index = false)
    private String avatar;

    @Field(type = FieldType.Keyword, index = false)
    private Timestamp createTime;

    @Field(type = FieldType.Keyword, index = false)
    private Integer readSize = 0;

    @Field(type = FieldType.Keyword, index = false)
    private Integer commentSize = 0;

    @Field(type = FieldType.Keyword, index = false)
    private Integer voteSize = 0;

    @Field(type = FieldType.Text, fielddata = true)
    private String tags;

    protected EsBlog() {
        //JPA的规范：要求无参构造函数设为 protected 防止直接使用
    }

    public EsBlog(Long blogId, String title, String summary, String content, String username,
                  String avatar, Timestamp createTime, Integer readSize, Integer commentSize,
                  Integer voteSize, String tags) {
        this.blogId = blogId;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.username = username;
        this.avatar = avatar;
        this.createTime = createTime;
        this.readSize = readSize;
        this.commentSize = commentSize;
        this.voteSize = voteSize;
        this.tags = tags;
    }

    public EsBlog(Blog blog) {

        this.blogId = blog.getId();
        this.title = blog.getTitle();
        this.summary = blog.getSummary();
        this.content = blog.getContent();
        this.username = blog.getUser().getUsername();
        this.avatar = blog.getUser().getAvatar();
        this.createTime = blog.getCreateTime();
        this.readSize = blog.getReadSize();
        this.commentSize = blog.getCommentSize();
        this.voteSize = blog.getVoteSize();
        this.tags = blog.getTags();
    }

    public void update(Blog blog) {
        this.blogId = blog.getId();
        this.title = blog.getTitle();
        this.summary = blog.getSummary();
        this.content = blog.getContent();
        this.username = blog.getUser().getUsername();
        this.avatar = blog.getUser().getAvatar();
        this.createTime = blog.getCreateTime();
        this.readSize = blog.getReadSize();
        this.commentSize = blog.getCommentSize();
        this.voteSize = blog.getVoteSize();
        this.tags = blog.getTags();
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

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getReadSize() {
        return readSize;
    }

    public void setReadSize(Integer readSize) {
        this.readSize = readSize;
    }

    public Integer getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(Integer commentSize) {
        this.commentSize = commentSize;
    }

    public Integer getVoteSize() {
        return voteSize;
    }

    public void setVoteSize(Integer voteSize) {
        this.voteSize = voteSize;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return String.format("EsBlog[blogId=%s,title='%s',summary='%s',content='%s']", blogId, title, summary, content);
    }
}
