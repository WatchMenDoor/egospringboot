package com.ego.service.impl;

import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.pojo.TbItemParamItem;
import com.ego.service.TbItemParamItemService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * @Auther: liuxw
 * @Date: 2019/8/8
 * @Description: com.ego.service.impl
 * @version: 1.0
 */
@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {

    @Reference
    TbItemParamItemDubboService tbItemParamItemDubboService;



    @Override
    public EgoResult selectOne(Long itemid) {
        TbItemParamItem item = tbItemParamItemDubboService.seletByItemID(itemid);

        if (item!=null){
            return EgoResult.ok(item);
        }else{
            return EgoResult.error("未找到商品规格信息！");
        }

    }
}
