package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endPoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            InputStream inputStream = file.getInputStream();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //获取文件名称
            String filename = file.getOriginalFilename();
            filename = uuid+filename;
            String datePath = new DateTime().toString("yyyy/MM/dd");
            filename = datePath + "/" + filename;
            ossClient.putObject(bucketName, filename, inputStream);
            ossClient.shutdown();
            //https://edu-sls.oss-cn-beijing.aliyuncs.com/03.jpg
            String url = "https://" + bucketName + "." + endPoint + "/" + filename;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
