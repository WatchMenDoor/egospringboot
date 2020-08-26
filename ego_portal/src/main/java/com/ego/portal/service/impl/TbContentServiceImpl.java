package com.ego.portal.service.impl;

import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.portal.service.TbContentService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: liuxw
 * @Date: 2019/8/12
 * @Description: com.ego.portal.service.impl
 * @version: 1.0
 */
@Service
public class TbContentServiceImpl implements TbContentService {

    @Reference
    private TbContentDubboService tbContentDubboService;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Value("${bigad.redis.key}")
    private String redisKey;
    @Value("${bigad.mysql.id}")
    private Long mysqlId;


    @Override
    public String showImg() {
        if (!redisTemplate.hasKey(redisKey)){
            List<TbContent> list = tbContentDubboService.showAllContent(mysqlId);
            List<Map<String,Object>> listad = new ArrayList<>();
            for (TbContent tc :list){
                Map<String,Object> map = new HashMap<>();
                map.put("srcB", tc.getPic2());
                map.put("height", 240);
                map.put("alt", "图片加载失败");
                map.put("width", 670);
                map.put("src",tc.getPic());
                map.put("widthB",670);
                map.put("heightB",240);
                map.put("href",tc.getUrl());
                listad.add(map);


            }

            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
            redisTemplate.opsForValue().set(redisKey, listad);
        }
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        String value = redisTemplate.opsForValue().get(redisKey).toString();

        return value;
    }
}
