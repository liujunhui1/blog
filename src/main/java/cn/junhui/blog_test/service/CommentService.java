package cn.junhui.blog_test.service;

import cn.junhui.blog_test.domain.Comment;

import java.util.Optional;

/**
 * 军辉
 * 2019-02-11 11:31
 */
public interface CommentService {

    /*
    根据 id 获取Comment
     */
    Optional<Comment> getCommentById(Long id);

    void removeComment(Long id);

}
