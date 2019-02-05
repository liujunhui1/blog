package cn.junhui.blog_test.repository;

import cn.junhui.blog_test.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 军辉
 * 2019-02-05 18:27
 * Authority的资源科接口，继承自 JpaRepository
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
