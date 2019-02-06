package cn.junhui.blog_test.controller;

import cn.junhui.blog_test.domain.File;
import cn.junhui.blog_test.service.FileService;
import jdk.internal.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 军辉
 * 2019-02-06 19:51
 */
@Controller
@RequestMapping("/files")
@CrossOrigin(origins = "", maxAge = 3600)//允许所有域名访问
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${server.address}")
    private String serviceAddress;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping
    public String index(Model model) {
        //展示最新的10条数据
        model.addAttribute("files", fileService.listFilesByPage(0, 10));
        return "index";
    }

    @ResponseBody
    @GetMapping("/{pageIndex}/{pageSize}")
    public List<File> listFileByPage(@PathVariable int PageIndex,
                                     @PathVariable int pageSize) {
        return fileService.listFilesByPage(PageIndex, pageSize);
    }
}
