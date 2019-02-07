package cn.junhui.blog_test.domain;


import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

/**
 * 军辉
 * 2019-02-06 19:14
 * 文档实体类
 */
@Document
public class File {

    @Id
    private String id;
    private String name;
    private String contentType;
    private long size;
    private Date uploadDate;
    private String md5;
    private Binary content;//文件内容（是使用 Binary类型进来存储的）
    private String path;//文件路径

    protected File() {
    }

    public File(String name, String contentType, long size, Binary content) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Binary getContent() {
        return content;
    }

    public void setContent(Binary content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, contentType, size, uploadDate, md5, id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        File file = (File) obj;
        return java.util.Objects.equals(size, file.size)
                && java.util.Objects.equals(name, file.name)
                && java.util.Objects.equals(contentType, file.contentType)
                && java.util.Objects.equals(uploadDate, file.uploadDate)
                && java.util.Objects.equals(md5, file.md5)
                && java.util.Objects.equals(id, file.id);


    }

    @Override
    public String toString() {
        return "File{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", contentType='" + contentType + '\'' +
                ", size=" + size +
                ", uploadDate=" + uploadDate +
                ", md5='" + md5 + '\'' +
                ", content=" + content +
                ", path='" + path + '\'' +
                '}';
    }
}
