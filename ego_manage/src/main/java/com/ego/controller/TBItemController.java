package com.ego.controller;

import com.ego.commons.pojo.EasyUiDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItem;
import com.ego.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: liuxw
 * @Date: 2019/8/6
 * @Description: com.ego.controller
 * @version: 1.0
 */
@RestController
public class TBItemController {

    @Autowired
    private TbItemService tbItemService;

    @RequestMapping("/item/list")
    public EasyUiDatagrid showItem(int page,int rows){

        return tbItemService.show(page,rows);

    }


    @RequestMapping("/rest/item/reshelf")
    public Object reshelf(String ids){

        return tbItemService.updateStatus(ids,(byte)1 );

    }

    @RequestMapping("/rest/item/instock")
    public Object instock(String ids){

        return tbItemService.updateStatus(ids,(byte)2 );

    }

    @RequestMapping("/rest/item/delete")
    public Object delete(String ids){

        return tbItemService.updateStatus(ids,(byte)3 );

    }

    @RequestMapping("/rest/item/update")
    public EgoResult update (TbItem item,String desc,String itemParams,Long itemParamId){

        return tbItemService.update(item,desc,itemParams,itemParamId);

    }

    @RequestMapping("/item/save")
    public EgoResult save (TbItem item,String desc,String itemParams){

        return tbItemService.insert(item,desc,itemParams);




    }





}
