package com.emilia.reggie.controller;

import com.emilia.reggie.common.R;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("common")
public class CommonController {

    @Value("${reggie.path}")
    private String path;

    //1.文件上传:本地->服务器(这时浏览器是看不到图片的)
    @PostMapping("upload")
    public R<String> upload(MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();
        int indexOf = originalFilename.lastIndexOf(".");
        String substring = originalFilename.substring(indexOf);
        String uuidFilename = UUID.randomUUID() + substring;

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File destFile = new File(dir, uuidFilename);
        file.transferTo(destFile);
        return R.success(uuidFilename);

    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg"); //通知浏览器当前输出的内容是图片
        //1.获取目标文件的输入流
        File file = new File(path, name);
        FileInputStream fileInputStream = new FileInputStream(file);
        //2. 获取repsonse的输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //3. 边读边写
        IOUtils.copy(fileInputStream, outputStream);

        //关闭资源
        fileInputStream.close();
    }
}
