package com.yuu.recruit.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传控制器
 *
 * @author by yuu
 * @Classname UploadController
 * @Date 2019/10/14 1:37
 * @see com.yuu.recruit.controller
 */
@Controller
public class UploadController {

    private static final String ENDPOINT = "oss-cn-shenzhen.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAI4FkTtw6KQXMzAqJGkqZp";
    private static final String ACCESS_KEY_SECRET = "woaIN9cCyTdxx4k1KTRFM6ySNzftm7";
    private static final String BUCKET_NAME = "recruit1";

    /**
     * 图片上传
     *
     * @param dropzFile
     * @param request
     * @return
     */
    @PostMapping("upload")
    @ResponseBody
    public Map<String, Object> upload(MultipartFile dropzFile, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> result = new HashMap<>();
        String fileName = dropzFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newName = UUID.randomUUID() + "." + suffix;
        OSS client = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            client.putObject(new PutObjectRequest(BUCKET_NAME, newName, new ByteArrayInputStream(dropzFile.getBytes())));
            // 上传文件路径 = http://BUCKET_NAME.ENDPOINT/自定义路径/fileName
            String filePath = "http://" + BUCKET_NAME + "." + ENDPOINT + "/" + newName;
            result.put("filePath", filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
        return result;
    }

    /**
     * 文件上传
     *
     * @param uploadFile
     * @param request
     * @return
     */
    @PostMapping("uploadFile")
    @ResponseBody
    public Map<String, Object> uploadFile(MultipartFile upload, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> result = new HashMap<>();
        String fileName = upload.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newName = UUID.randomUUID() + "." + suffix;
        OSS client = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            client.putObject(new PutObjectRequest(BUCKET_NAME, newName, new ByteArrayInputStream(upload.getBytes())));
            // 上传文件路径 = http://BUCKET_NAME.ENDPOINT/自定义路径/fileName
            String filePath = "http://" + BUCKET_NAME + "." + ENDPOINT + "/" + newName;
            result.put("filePath", filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
        return result;
    }

}
