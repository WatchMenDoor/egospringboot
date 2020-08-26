package com.ego.service.impl;

import com.ego.commons.pojo.EasyUiDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TBItemCatDubboService;
import com.ego.dubbo.service.TBItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.service.TbItemService;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: liuxw
 * @Date: 2019/8/6
 * @Description: com.ego.service.impl
 * @version: 1.0
 */
@Service
public class TbItemServiceImpl implements TbItemService{

    @Reference
    private TbItemDubboService tbItemDubboService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Reference
    private TBItemCatDubboService tbItemCatDubboService;
    @Reference
    private TBItemDescDubboService tbItemDescDubboService;

    @Value("${myredis.itemkey}")
    private String key;

    @Override
    public EasyUiDatagrid show(int page, int rows) {
        return tbItemDubboService.showItem(page, rows);
    }

    @Override
    public EgoResult updateStatus(String ids, Byte status) {

        int i = tbItemDubboService.updateBy(ids, status);
        if (i==1){
            //同步redis
            String[] idArr = ids.split(",");
            for (String id: idArr) {
                redisTemplate.delete(key+":"+id);
            }
            //同步solr

            new Thread(){
                @Override
                public void run() {
                    if((byte)status==1){//上架
                        for(String id :idArr){
                            TbItem item = tbItemDubboService.selectByid(Long.parseLong(id));
                            Map<String,String> map = new HashMap<String,String>();
                            map.put("id",id+"");
                            map.put("item_title",item.getTitle());
                            map.put("item_sell_point",item.getSellPoint());
                            map.put("item_price",item.getPrice()+"");
                            map.put("item_image",item.getImage());
                            map.put("item_category_name",tbItemCatDubboService.selectByid(item.getCid()).getName());
                            map.put("item_desc",tbItemDescDubboService.selectById(item.getId()).getItemDesc());
                            HttpClientUtil.doPost("http://localhost:8083/save", map);
//                    long status = Long.parseLong(s);
//                    if(status==1){
//                        System.out.println("成功");
//                    }
                        }
                    }else{
                        List<String> idsList = new ArrayList<>();
                        for(String id :idArr){
                            idsList.add(id);
                        }
                        String json = JsonUtils.objectToJson(idsList);
                        HttpClientUtil.doPostJson("http://localhost:8083/delete",json);
                    }
                }
            }.start();




            return EgoResult.ok();
        }


        return EgoResult.error("操作失败");
    }


    @Override
    public EgoResult update(TbItem item, String desc,String itemParams,Long itemParamId) {
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(item.getId());
        tbItemParamItem.setId(itemParamId);
        tbItemParamItem.setParamData(itemParams);
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(item.getId());
        tbItemDesc.setItemDesc(desc);
        int i = tbItemDubboService.update(item, tbItemDesc,tbItemParamItem);
        if (i==1){
            //同步redis
            redisTemplate.delete(key+":"+item.getId());
            //同步solr
            Map<String,String> map = new HashMap<String,String>();
            map.put("id",item.getId()+"");
            map.put("item_title",item.getTitle());
            map.put("item_sell_point",item.getSellPoint());
            map.put("item_price",item.getPrice()+"");
            map.put("item_image",item.getImage());
            map.put("item_category_name",tbItemCatDubboService.selectByid(item.getCid()).getName());
            map.put("item_desc",desc);
            HttpClientUtil.doPost("http://localhost:8083/save",map);
            return EgoResult.ok();
        }else{
            return EgoResult.error("修改失败！");
        }



    }

    @Override
    public EgoResult insert(TbItem item, String desc,String itemParams) {
        TbItemDesc descobj = new TbItemDesc();
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setParamData(itemParams);
        descobj.setItemDesc(desc);
        item.setStatus((byte)1);
        long i = tbItemDubboService.insertItem(item, descobj,tbItemParamItem);
        if (i!=0){
            //调用ego_search项目的控制器
            Map<String,String> map = new HashMap<String,String>();
            map.put("id",i+"");
            map.put("item_title",item.getTitle());
            map.put("item_sell_point",item.getSellPoint());
            map.put("item_price",item.getPrice()+"");
            map.put("item_image",item.getImage());
            map.put("item_category_name",tbItemCatDubboService.selectByid(item.getCid()).getName());
            map.put("item_desc",desc);

            HttpClientUtil.doPost("http://localhost:8083/save",map);
            return EgoResult.ok();
        }

        return EgoResult.error("新增失败");
    }
}
