package cn.junhui.blog_test.repository;

import cn.junhui.blog_test.domain.User;

import java.util.List;

/**
 * 军辉
 * 2019-01-29 19:23
 */
public interface UserRepository {

    /*
    新增或修改用户
     */
    User saveOrUpdateUser(User user);

    /*
    根据用户id删除用户
     */
    void deleteUserById(Long id);

    /*
    根据用户id获取用户
     */
    User getUserById(Long id);

    /*
    获取所有用户列表
     */
    List<User> listUser();
}
