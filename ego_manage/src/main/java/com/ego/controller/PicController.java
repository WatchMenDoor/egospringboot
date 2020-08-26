package com.ego.controller;

import com.ego.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Auther: liuxw
 * @Date: 2019/8/7
 * @Description: com.ego.controller
 * @version: 1.0
 */
@RestController
public class PicController {
    @Autowired
    private PicService picService;

    @PostMapping("/pic/upload")
    public Map<String,Object> upload(MultipartFile uploadFile){
        return picService.upload(uploadFile);
    }
}
