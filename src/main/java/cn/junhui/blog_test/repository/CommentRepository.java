package cn.junhui.blog_test.repository;

import cn.junhui.blog_test.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 军辉
 * 2019-02-11 11:30
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
