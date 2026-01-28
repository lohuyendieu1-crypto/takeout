package com.sky.utils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayInputStream;

@Data
@AllArgsConstructor
@Slf4j
public class AwsS3Util {

 private String endpoint;
 private String accessKey;
 private String secretKey;
 private String bucketName;
 private String domain;

 /**
  * 文件上傳
  * @param bytes 文件字節數組
  * @param objectName 文件名 (在 Controller 生成好傳進來)
  * @return 文件訪問路徑
  */
 public String upload(byte[] bytes, String objectName) {
  // 1. 初始化 S3 客戶端 (用來連 Cloudflare R2)
  BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

  AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
          .withCredentials(new AWSStaticCredentialsProvider(credentials))
          // R2 不分區域，第二個參數 region 隨便寫個 "auto" 即可
          .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, "auto"))
          .build();

  try {
   // 2. 創建請求並上傳
   ObjectMetadata metadata = new ObjectMetadata();
   metadata.setContentLength(bytes.length);
   // 這裡可以不設 ContentType，R2 會自動識別，或者你可以像舊代碼那樣傳進來，但為了簡單先這樣

   s3Client.putObject(new PutObjectRequest(bucketName, objectName, new ByteArrayInputStream(bytes), metadata));

   // 3. 拼接返回路徑
   // R2 的特點：上傳是去 endpoint，但訪問是去 domain (pub-xxx.r2.dev)
   StringBuilder stringBuilder = new StringBuilder(domain);
   if (!domain.endsWith("/")) {
    stringBuilder.append("/");
   }
   stringBuilder.append(objectName);

   // 注意：如果是私有桶，這裡其實不應該返回 domain 拼接的路徑，應該只返回 objectName
   // 但為了兼容你現在的公開桶邏輯，我們還是返回完整路徑
   log.info("文件上傳成功: {}", stringBuilder.toString());
   return stringBuilder.toString();

  } catch (Exception e) {
   log.error("上傳文件到 R2 失敗", e);
   throw new RuntimeException("文件上傳失敗");
  }
 }
}