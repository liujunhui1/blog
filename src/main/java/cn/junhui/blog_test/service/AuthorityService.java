package cn.junhui.blog_test.service;

import cn.junhui.blog_test.domain.Authority;

import java.util.Optional;

/**
 * 军辉
 * 2019-02-05 18:29
 */
public interface AuthorityService {

    /*
    根据Id查询Authority
     */
    Optional<Authority> getAuthorityById(Long id);
}
