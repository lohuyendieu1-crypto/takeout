package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AwsS3Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 通用接口
 */
@Api(tags="通用接口")
@Slf4j
@RestController
@RequestMapping("/admin/common")
public class CommonController {

    @Autowired
    private AwsS3Util awsS3Util;

    @ApiOperation("文件上傳")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        log.info("文件上傳,{}",file);
        try {
            // 原始文件名
            String originalFilename = file.getOriginalFilename();
            // 截取原始文件名的後綴名
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = UUID.randomUUID().toString() + extension;
            String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            objectName = datePath + "/" + objectName;
            String filePath = awsS3Util.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上傳異常: ", e);

        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
