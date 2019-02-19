package cn.junhui.blog_test.controller;

import cn.junhui.blog_test.domain.File;
import cn.junhui.blog_test.service.FileService;
import cn.junhui.blog_test.util.MD5Util;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;


/**
 * 军辉
 * 2019-02-06 19:51
 */
@Controller
@RequestMapping("/files")
@CrossOrigin(origins = "*", maxAge = 3600)//允许所有域名访问
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping
    public String index(Model model) {
        //展示最新的10条数据
        model.addAttribute("files", fileService.listFilesByPage(0, 10));
        return "index";
    }

    /*
    分页查询文件
     */
    @ResponseBody
    @GetMapping("/{pageIndex}/{pageSize}")
    public List<File> listFileByPage(@PathVariable int PageIndex,
                                     @PathVariable int pageSize) {
        return fileService.listFilesByPage(PageIndex, pageSize);
    }

    /*
    获取文件信息
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> serveFile(@PathVariable String id) {
        Optional<File> fileOption = fileService.getFileById(id);
        if ((fileOption).isPresent()) {//判断是否有值
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName =\"" + fileOption.get().getName() + "\"")
                    .header(HttpHeaders.CONTENT_DISPOSITION, "application/octet-stream")
                    .header(HttpHeaders.CONTENT_LENGTH, fileOption.get().getSize() + "")
                    .header("Connection", "close")
                    .body(fileOption.get().getContent().getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }
    }


    /*
    在线显示文件
     */
    @GetMapping("/view/{id}")
    @ResponseBody
    public ResponseEntity<Object> serveFileOnline(@PathVariable String id) {
        Optional<File> fileOptional = fileService.getFileById(id);
        if (fileOptional.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + fileOptional.get().getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, fileOptional.get().getContentType())
                    .header(HttpHeaders.CONTENT_LENGTH, fileOptional.get().getSize() + "")
                    .header("Connection", "close")
                    .body(fileOptional.get().getContent().getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }
    }

    /*
    上传
     */
    /*@PostMapping("/")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        File returnFile = null;
        try {
            File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(), new Binary(file.getBytes()));
            f.setMd5(DigestUtils.md5DigestAsHex(file.getInputStream()));
            //f.setMd5(MD5Util);
            returnFile = fileService.saveFile(f);
            String path = "//" + serverAddress + ":" + serverPort + "/files/view/" + returnFile.getId();
            return ResponseEntity.status(HttpStatus.OK).body(path);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
*/

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        try {
            File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(),
                    new Binary(file.getBytes()));
            f.setMd5(MD5Util.getMD5(file.getInputStream()));
            fileService.saveFile(f);
        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Your " + file.getOriginalFilename() + " is wrong!");
            return "redirect:/";
        }

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    /**
     * 上传接口
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        System.out.println("这是上传文件的方法");
        System.out.println("文件的名字是:" + file.getOriginalFilename());
        File returnFile = null;
        try {
            File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(),
                    new Binary(file.getBytes()));
            f.setMd5(MD5Util.getMD5(file.getInputStream()));
            returnFile = fileService.saveFile(f);
            String path = "//" + serverAddress + ":" + serverPort + "/view/" + returnFile.getId();
            return ResponseEntity.status(HttpStatus.OK).body(path);

        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

    }

    /*
    删除文件
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteFile(@PathVariable String id) {

        fileService.removeFile(id);
        return ResponseEntity.status(HttpStatus.OK).body("DELETE Success!");


    }


}
