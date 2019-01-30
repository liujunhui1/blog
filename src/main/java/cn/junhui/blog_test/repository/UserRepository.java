package cn.junhui.blog_test.repository;

import cn.junhui.blog_test.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 军辉
 * 2019-01-29 19:23
 *
 * 修改继承于 CrudRepository
 * Spring Data JPA 已经帮用户做了实现，因此用户不需要做任何实现，
 * 甚至都不需要在UserRepository中定义任何方法，就可以直接删除了之前定义的 UserRepositoryImpl
 */
public interface UserRepository extends CrudRepository<User,Long> {
/*
    *//*
    新增或修改用户
     *//*
    User saveOrUpdateUser(User user);

    *//*
    根据用户id删除用户
     *//*
    void deleteUserById(Long id);

    *//*
    根据用户id获取用户
     *//*
    User getUserById(Long id);

    *//*
    获取所有用户列表
     *//*
    List<User> listUser();*/
}
