package cn.junhui.blog_test.service;

import cn.junhui.blog_test.domain.Vote;

import java.util.Optional;

/**
 * 军辉
 * 2019-02-12 15:26
 * 点赞的服务接口
 */
public interface VoteService {

    /*
    根据 id 获取 Vote
     */
    Optional<Vote> getVoteById(Long id);

    /*
    取消 vote
     */
    void removeVote(Long id);
}
