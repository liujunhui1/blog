package cn.junhui.blog_test.repository.repositoryImpl;

import cn.junhui.blog_test.domain.User;
import cn.junhui.blog_test.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 军辉
 * 2019-01-29 19:38
 */
/*
@Repository用于标识UserRepository是一个可注入的bean
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    /*
    用来生成一个递增的id，座位用户唯一的id
     */
    private static AtomicLong counter = new AtomicLong();


    /*
    模拟数据的存储
     */
    private final ConcurrentMap<Long, User> userMap = new ConcurrentHashMap<>();

    @Override
    public User saveOrUpdateUser(User user) {
        Long id = user.getId();
        if (null == id) {
            id = counter.incrementAndGet();
            user.setId(id);
        }
        this.userMap.put(id, user);
        return user;
    }

    @Override
    public void deleteUserById(Long id) {
        this.userMap.remove(id);
    }

    @Override
    public User getUserById(Long id) {
        return this.userMap.get(id);
    }

    @Override
    public List<User> listUser() {
        return new ArrayList<User>(this.userMap.values());
    }
}
