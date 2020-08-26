package com.ego.controller;

import com.ego.commons.pojo.EasyUiDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemParam;
import com.ego.service.TbItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: liuxw
 * @Date: 2019/8/7
 * @Description: com.ego.controller
 * @version: 1.0
 */
@RestController
public class TBItemParamController {


    @Autowired
    private TbItemParamService tbItemParamService;

    @RequestMapping("/item/param/list")
    public EasyUiDatagrid showConfi(int page,int rows){

        return tbItemParamService.showItem(page, rows);

    }

    @RequestMapping("/item/param/query/itemcatid/{id}")
    public Object showItemCat(@PathVariable Long id){
        return tbItemParamService.findOne(id);

    }


    //新增
    @RequestMapping("/item/param/save/{id}")
    public EgoResult save (TbItemParam tbItemParam,@PathVariable Long id){

        tbItemParam.setItemCatId(id);
        return tbItemParamService.save(tbItemParam);

    }
    
    

    //添加删除
    @RequestMapping("/item/param/delete")
    public EgoResult delete (Long ids){


        return tbItemParamService.delete(ids);

    }
}
