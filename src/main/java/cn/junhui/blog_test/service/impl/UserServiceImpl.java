package cn.junhui.blog_test.service.impl;

import cn.junhui.blog_test.domain.User;
import cn.junhui.blog_test.repository.UserRepository;
import cn.junhui.blog_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 军辉
 * 2019-02-04 13:50
 *
 * @Transactional 注解只能应用到 public 方法才有效。
 * 表明此方法支持事务管理
 * <p>
 * public final class Optional<T> extends Object
 * 可能包含或不包含非空值的容器对象。 如果一个值存在， isPresent()将返回true和get()将返回值。
 * 提供依赖于存在或不存在包含值的其他方法，
 * 例如orElse() （如果值不存在则返回默认值）和ifPresent() （如果值存在则执行代码块）。
 * 这是一个value-based课; 使用身份敏感的操作（包括引用相等（的==上的实例），标识哈希码，或同步）
 * Optional可具有不可预测的结果，应当避免。
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Page<User> listUsersByNameLike(String name, Pageable pageable) {
        name = "%" + name + "%";
        Page<User> users = userRepository.findByNameLike(name, pageable);
        return users;
    }

    @Override
    public List<User> listUsersByUsernames(Collection<String> usernames) {
        // //这里可以可以通过username（登录时输入的用户名）然后到数据库中找到对应的用户信息，
        // 并构建成我们自己的UserInfo来返回。
        //return userRepository.findByUsername();
        // System.out.println("userServiceImpl重载的 listUsersByUsernames 的 usernames:" + usernames);
        return userRepository.findByUsernameIn(usernames);
    }


    /*
    重载的是 UserDetailsService 的方法
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // System.out.println("userServiceImpl重载的 loadUserByUsername 的 username:" + username);
        // System.out.println("**************" + userRepository.findByUsername(username) + "*********************");
        return userRepository.findByUsername(username);
    }

}
