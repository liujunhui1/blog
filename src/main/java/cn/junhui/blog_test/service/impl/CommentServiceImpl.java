package cn.junhui.blog_test.service.impl;

import cn.junhui.blog_test.domain.Comment;
import cn.junhui.blog_test.repository.CommentRepository;
import cn.junhui.blog_test.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 军辉
 * 2019-02-11 11:33
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;


    @Override
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public void removeComment(Long id) {
        commentRepository.deleteById(id);
    }
}
