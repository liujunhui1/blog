package cn.junhui.blog_test.repository;

import cn.junhui.blog_test.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

/**
 * 军辉
 * 2019-01-29 19:23
 * <p>
 * 修改继承于 CrudRepository
 * Spring Data JPA 已经帮用户做了实现，因此用户不需要做任何实现，
 * 甚至都不需要在UserRepository中定义任何方法，就可以直接删除了之前定义的 UserRepositoryImpl
 * <p>
 * <p>
 * 修改继承于JpaRepository
 * 这样 UserRepository 就有了分页的功能
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /*
    根据用户姓名分页查询用户列表
     */
    Page<User> findByNameLike(String name, Pageable pageable);

    /*
    根据用户账户查询用户
     */
    User findByUsername(String username);


    /**
     * 根据名称列表查询用户列表
     *
     * @param usernames
     * @return
     */
    List<User> findByUsernameIn(Collection<String> usernames);


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
