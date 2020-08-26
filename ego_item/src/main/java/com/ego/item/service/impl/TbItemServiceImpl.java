package com.ego.item.service.impl;

import com.ego.commons.pojo.SearchResultEntity;
import com.ego.dubbo.service.TbItemDubboService;

import com.ego.item.service.TbItemService;
import com.ego.pojo.TbItem;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

/**
 * @Auther: liuxw
 * @Date: 2019/8/15
 * @Description: com.ego.item.service.impl
 * @version: 1.0
 */
@Service
public class TbItemServiceImpl implements TbItemService {

    @Reference
    private TbItemDubboService tbItemDubboService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Value("${myredis.itemkey}")
    private String key;

    @Override
    public SearchResultEntity selectItem(Long id) {

        String redisKey = key+":"+id;
        if (!redisTemplate.hasKey(redisKey)){
            SearchResultEntity entity = new SearchResultEntity();
            TbItem tbItem = tbItemDubboService.selectByid(id);
            BeanUtils.copyProperties(tbItem,entity);
            entity.setId(tbItem.getId()+"");

            String image = tbItem.getImage();
            entity.setImages(image!=null&&!image.equals("")?image.split(","):new String[0]);

            redisTemplate.opsForValue().set(redisKey, entity);
        }
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<SearchResultEntity>(SearchResultEntity.class));
        SearchResultEntity sre = (SearchResultEntity) redisTemplate.opsForValue().get(redisKey);

        return sre;
    }
}
