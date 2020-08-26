package com.ego.service.impl;

import com.ego.commons.utils.FtpUtil;
import com.ego.service.PicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: liuxw
 * @Date: 2019/8/7
 * @Description: com.ego.service.impl
 * @version: 1.0
 */
@Service
public class PicServiceImpl implements PicService {


    @Value("${vsftpd.host}")
    private String host;

    @Value("${vsftpd.port}")
    private int port;

    @Value("${vsftpd.username}")
    private String username;

    @Value("${vsftpd.password}")
    private String password;

    @Value("${vsftpd.basePath}")
    private String basePath;

    @Value("${vsftpd.filePath}")
    private String filePath;



    @Override
    public Map<String, Object> upload(MultipartFile file) {
        Map<String,Object> map = new HashMap<>();
        try {
            String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = UUID.randomUUID()+substring;

            boolean b = FtpUtil.uploadFile(host, port, username, password, basePath, filePath, fileName, file.getInputStream());
            if (b){
                map.put("error", 0);
                map.put("url", "http://"+host+"/"+fileName);
            }
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("error", 1);
        map.put("message", "图片上传失败");
        return map;
    }
}
