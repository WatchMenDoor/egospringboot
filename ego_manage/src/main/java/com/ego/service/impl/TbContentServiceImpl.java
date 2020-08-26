package com.ego.service.impl;

import com.ego.commons.pojo.EasyUiDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.service.TbContentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/8
 * @Description: com.ego.service.impl
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




    @Override
    public EasyUiDatagrid showAllContent(Long cid,int page, int rows) {
        PageHelper.startPage(page, rows);
        EasyUiDatagrid datagrid = new EasyUiDatagrid();
        List<TbContent> list = tbContentDubboService.showAllContent(cid);

        PageInfo<TbContent> pi = new PageInfo<>(list);
        datagrid.setTotal(tbContentDubboService.total(cid));
        datagrid.setRows(pi.getList());
        return datagrid;
    }

    @Override
    public EgoResult delete(Long id) {
        int delete = tbContentDubboService.delete(id);
        if (delete==1){
            return EgoResult.ok();
        }
        return EgoResult.error("刪除失敗");
    }

    @Override
    public EgoResult save(TbContent tbContent) {


        int i = tbContentDubboService.save(tbContent);
        if (i==1){
            //同步更新
            if (redisTemplate.hasKey(redisKey)){
                List<LinkedHashMap<String,Object>> list = (List<LinkedHashMap<String, Object>>) redisTemplate.opsForValue().get(redisKey);
                LinkedHashMap<String,Object> map = new LinkedHashMap<>();
                map.put("srcB",tbContent.getPic2());
                map.put("height",240);
                map.put("alt","图片加载失败");
                map.put("width",670);
                map.put("src",tbContent.getPic());
                map.put("widthB",670);
                map.put("heightB",240);
                map.put("href",tbContent.getUrl());
                list.add(map);
                redisTemplate.opsForValue().set(redisKey,list);

            }
            return EgoResult.ok();
        }
        return EgoResult.error("新增失敗");

    }

    @Override
    public EgoResult update(TbContent tbContent) {
        int i = tbContentDubboService.update(tbContent);
        if (i==1){
            return EgoResult.ok();
        }
        return EgoResult.error("更新失敗");

    }
}
