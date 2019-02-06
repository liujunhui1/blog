package cn.junhui.blog_test.service;

import cn.junhui.blog_test.domain.File;

import java.util.List;
import java.util.Optional;

/**
 * 军辉
 * 2019-02-06 19:34
 */
public interface FileService {
    /*
    保存文件
     */
    File saveFile(File file);

    void removeFile(String id);

    Optional<File> getFileById(String id);

    /*
    分页查询，按上传时间降序
     */
    List<File> listFilesByPage(int pageIndex, int pageSize);

}
