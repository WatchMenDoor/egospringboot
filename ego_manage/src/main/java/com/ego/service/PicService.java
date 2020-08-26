package com.ego.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Auther: liuxw
 * @Date: 2019/8/7
 * @Description: com.ego.service
 * @version: 1.0
 */
public interface PicService {

    Map<String,Object> upload(MultipartFile file);
}
