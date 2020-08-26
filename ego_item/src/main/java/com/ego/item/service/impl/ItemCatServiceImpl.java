package com.ego.item.service.impl;

import com.ego.dubbo.service.TBItemCatDubboService;
import com.ego.item.pojo.PortalCatMenu;
import com.ego.item.service.ItemCatService;
import com.ego.pojo.TbItemCat;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: liuxw
 * @Date: 2019/8/12
 * @Description: com.ego.item.service.impl
 * @version: 1.0
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Reference
   private TBItemCatDubboService tbItemCatDubboService;



    @Override
    public Map<String, Object> showMenu() {


        Map<String,Object> map = new HashMap<>();

        map.put("data", getMenu(tbItemCatDubboService.selectByPid(0L)));
        return map;
    }

    private List<Object> getMenu(List<TbItemCat> list) {
        List<Object> listResult = new ArrayList<>();
        for (TbItemCat cat: list) {
            if (cat.getIsParent()){
                PortalCatMenu pcm = new PortalCatMenu();
                pcm.setU("/products/"+cat.getId()+".html");
                pcm.setN("<a href='/products/"+cat.getId()+".html'>"+cat.getName()+"</a>");
                pcm.setI(getMenu(tbItemCatDubboService.selectByPid(cat.getId())));
                listResult.add(pcm);

            }else{
                listResult.add("/product/"+cat.getId()+"|"+cat.getName());
            }

        }
        return listResult;


    }
}
