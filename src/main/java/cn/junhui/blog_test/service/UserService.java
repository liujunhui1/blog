package cn.junhui.blog_test.service;

import cn.junhui.blog_test.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
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

    /**
     * 根据用户名集合，查询用户详细信息列表
     *
     * @param usernames
     * @return
     */
    List<User> listUsersByUsernames(Collection<String> usernames);

}
