package cn.junhui.blog_test.service;

import cn.junhui.blog_test.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 军辉
 * 2019-02-04 13:42
 */

public interface UserService {

    /*
    新增 编辑 保存 用户
     */
    User saveOrUpdateUser(User user);


    User registerUser(User user);


    void removeUser(Long id);


    Optional<User> getUserById(Long id);

    /*
    根据用户名进行分页模糊查询
     */
    Page<User> listUsersByNameLike(String name, Pageable pageable);
}
