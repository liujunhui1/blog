package cn.junhui.blog_test.repository;

import cn.junhui.blog_test.domain.File;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 军辉
 * 2019-02-06 19:28
 * 无需自行实现该接口的功能，继承 MongoRepository 会自动实现接口中的方法
 */
public interface FileRepository extends MongoRepository<File, String> {
}
