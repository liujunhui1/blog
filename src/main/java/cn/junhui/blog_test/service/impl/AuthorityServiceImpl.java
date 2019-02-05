package cn.junhui.blog_test.service.impl;

import cn.junhui.blog_test.domain.Authority;
import cn.junhui.blog_test.repository.AuthorityRepository;
import cn.junhui.blog_test.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 军辉
 * 2019-02-05 18:30
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Optional<Authority> getAuthorityById(Long id) {
        return authorityRepository.findById(id);
    }
}
