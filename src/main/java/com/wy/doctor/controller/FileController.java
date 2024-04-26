package com.wy.doctor.controller;


import com.wy.doctor.config.MinioConfig;
import com.wy.doctor.utils.MinioUtil;
import com.wy.doctor.vo.Result;
import io.minio.messages.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/file")
public class FileController {


    @Autowired
    private MinioUtil minioUtil;
    @Autowired
    private MinioConfig prop;

    @GetMapping("/bucketExists")
    public Result<Boolean> bucketExists(@RequestParam("bucketName") String bucketName) {
        return Result.ok(minioUtil.bucketExists(bucketName));
    }

    @GetMapping("/makeBucket")
    public Result<Boolean> makeBucket(String bucketName) {
        return Result.ok(minioUtil.makeBucket(bucketName));
    }

    @GetMapping("/removeBucket")
    public Result<Boolean> removeBucket(String bucketName) {
        return Result.ok(minioUtil.removeBucket(bucketName));
    }
//获得所有桶
    @GetMapping("/getAllBuckets")
    public Result<List> getAllBuckets() {
        List<Bucket> allBuckets = minioUtil.getAllBuckets();
        return Result.ok(allBuckets);
    }
//上传
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        String objectName = minioUtil.upload(file);
        if (null != objectName) {
            return Result.ok((prop.getEndpoint() + "/" + prop.getBucketName() + "/" + objectName));
        }
        return Result.failed("上传失败");
    }
//    预览
    @GetMapping("/preview")
    public Result<String> preview(@RequestParam("fileName") String fileName) {
        String url = minioUtil.preview(fileName);
        return Result.ok(minioUtil.preview(fileName));
    }
//    下载
    @GetMapping("/download")
    public Result<Object> download(@RequestParam("fileName") String fileName, HttpServletResponse res) {
        minioUtil.download(fileName,res);
        return Result.ok();
    }

//    删除图片
    @PostMapping("/delete")
    public Result<String> remove(String url) {
        String objName = url.substring(url.lastIndexOf(prop.getBucketName()+"/") + prop.getBucketName().length()+1);
        minioUtil.remove(objName);
        return Result.ok(objName);
    }

}

