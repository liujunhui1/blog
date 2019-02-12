package cn.junhui.blog_test.repository;

import cn.junhui.blog_test.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 军辉
 * 2019-02-11 16:59
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
