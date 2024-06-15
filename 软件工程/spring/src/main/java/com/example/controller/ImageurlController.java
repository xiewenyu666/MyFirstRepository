package com.example.controller;

import com.example.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@CrossOrigin
@RestController
@RequestMapping()
public class ImageurlController {

    @PostMapping("/book/avatar")
    public Result uploadAvatarHandler(@RequestParam("avatar") MultipartFile uploadFile) throws IOException {
        // 定义文件保存的绝对路径
        String uploadPath = "C:\\Users\\86187\\IdeaProjects\\软件工程\\wms\\src\\assets";
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            System.out.println("上传头像路径不存在，正在创建...");
            uploadDir.mkdirs(); // 确保创建所有必要的父目录
        }

        if (uploadFile != null) {
            // 获得上传文件的文件名
            String oldName = uploadFile.getOriginalFilename();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            oldName = LocalDateTime.now().format(formatter) + oldName;
            System.out.println("[上传的文件名]：" + oldName);

            // 创建文件对象，指向绝对路径下的文件
            File avatar = new File(uploadPath + "\\" + oldName);

            try {
                // 保存图片
                uploadFile.transferTo(avatar);
                // 返回成功结果，附带文件的相对路径
                // 注意：这里的路径应该是相对于Web根目录的路径，或者是完整的URL
                return Result.success(oldName);
            } catch (IOException e) {
                e.printStackTrace();
                return Result.error("上传失败");
            }
        } else {
            System.out.println("上传的文件为空");
            return Result.error("文件传输错误");
        }
    }
    @PostMapping("/user/avatar")
    public Result uploaduserAvatarHandler(@RequestParam("avatar") MultipartFile uploadFile) throws IOException {
        // 定义文件保存的绝对路径
        String uploadPath = "C:\\Users\\86187\\IdeaProjects\\软件工程\\wms\\src\\userassets";
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            System.out.println("上传头像路径不存在，正在创建...");
            uploadDir.mkdirs(); // 确保创建所有必要的父目录
        }

        if (uploadFile != null) {
            // 获得上传文件的文件名
            String oldName = uploadFile.getOriginalFilename();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            oldName = LocalDateTime.now().format(formatter) + oldName;
            System.out.println("[上传的文件名]：" + oldName);

            // 创建文件对象，指向绝对路径下的文件
            File avatar = new File(uploadPath + "\\" + oldName);

            try {
                // 保存图片
                uploadFile.transferTo(avatar);
                // 返回成功结果，附带文件的相对路径
                // 注意：这里的路径应该是相对于Web根目录的路径，或者是完整的URL
                return Result.success(oldName);
            } catch (IOException e) {
                e.printStackTrace();
                return Result.error("上传失败");
            }
        } else {
            System.out.println("上传的文件为空");
            return Result.error("文件传输错误");
        }
    }

}
