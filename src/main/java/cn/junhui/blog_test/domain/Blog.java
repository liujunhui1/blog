package cn.junhui.blog_test.domain;

import com.github.rjeschke.txtmark.Processor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.awt.event.FocusEvent;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 军辉
 * 2019-02-09 12:12
 * <p>
 * Serializable
 * 类的序列化由实现java.io.Serializable接口的类启用。
 * 不实现此接口的类将不会使任何状态序列化或反序列化。
 * 可序列化类的所有子类型都是可序列化的。
 * 序列化接口没有方法或字段，仅用于标识可串行化的语义。
 */
@Entity
public class Blog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增长策略
    private Long id;

    @NotEmpty(message = "标题不能为空")
    @Size(min = 2, max = 50)
    @Column(nullable = false)//映射为字段，值不能为空
    private String title;

    @NotEmpty(message = "内容不能为空")
    @Size(min = 2, max = 300)
    @Column(nullable = false)
    private String summary;

    @Lob    //大对象，映射mysql的LongText的类型
    @Basic(fetch = FetchType.LAZY)  //懒加载
    @NotEmpty(message = "内容不能为空")
    @Size(min = 2)
    @Column(nullable = false)
    private String content;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty(message = "内容不能为空")
    @Size(min = 2)
    @Column(nullable = false)
    private String htmlContent; //将 md 转换为 html

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @CreationTimestamp //由 数据库自动创建时间
    private Timestamp createTime;

    @Column(nullable = false)
    private Integer readSize = 0;//阅读量

    @Column(nullable = false)
    private Integer commentSize = 0;//评论量

    @Column(nullable = false)
    private Integer voteSize = 0;//点赞量

    @Column(name = "tags", length = 100)
    private String tags;

    /*
    使 Blog 与 Comment建立起关系
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "blog_comment", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"))
    private List<Comment> comments;

    /*
    Blog 与 Vote 建立起关系
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "blog_vote", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "vote_id", referencedColumnName = "id"))
    private List<Vote> votes;

    /*
    Blog 与 Catalog 建立起关系
     */
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;


    protected Blog() {
    }

    public Blog(String title, String summary, String content) {
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        this.htmlContent = Processor.process(content);
        //将 Markdown 内容转换为 HTML 格式
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        this.commentSize = this.comments.size();
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }


    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    /*
            添加评论
             */
    public void addComment(Comment comment) {
        this.comments.add(comment);
        this.commentSize = this.comments.size();
    }

    /*
    删除评论
     */
    public void removeComment(Long commentId) {
        for (int index = 0; index < this.comments.size(); index++) {
            if (comments.get(index).getId() == commentId) {
                this.comments.remove(index);
                break;
            }
        }
        this.commentSize = comments.size();
    }

    /*
    点赞
     */
    public boolean addVote(Vote vote) {
        boolean isExist = false;
        for (int index = 0; index < this.votes.size(); index++) {
            if (this.votes.get(index).getUser().getId() == vote.getUser().getId()) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            this.votes.add(vote);
            this.voteSize = this.votes.size();
        }
        return isExist;
    }

    /*
    取消点赞
     */
    public void removeVote(Long voteId) {
        for (int i = 0; i < votes.size(); i++) {
            if (this.votes.get(i).getId() == voteId) {
                this.votes.remove(i);
                break;
            }
        }
        this.voteSize = this.votes.size();
    }


    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", htmlContent='" + htmlContent + '\'' +
                ", user=" + user +
                ", createTime=" + createTime +
                ", readSize=" + readSize +
                ", commentSize=" + commentSize +
                ", voteSize=" + voteSize +
                ", tags='" + tags + '\'' +
                '}';
    }
}
