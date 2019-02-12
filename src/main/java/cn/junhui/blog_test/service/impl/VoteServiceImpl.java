package cn.junhui.blog_test.service.impl;

import cn.junhui.blog_test.domain.Vote;
import cn.junhui.blog_test.repository.VoteRepository;
import cn.junhui.blog_test.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 军辉
 * 2019-02-12 15:28
 */
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Optional<Vote> getVoteById(Long id) {
        return voteRepository.findById(id);
    }

    @Override
    public void removeVote(Long id) {
        voteRepository.deleteById(id);
    }
}
