package com.ego.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.service.TbItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: liuxw
 * @Date: 2019/8/8
 * @Description: com.ego.controller
 * @version: 1.0
 */
@RestController
public class TbItemParamItemController {


    @Autowired
    private TbItemParamItemService tbItemParamItemService;

    @GetMapping("/rest/item/param/item/query/{id}")
    public EgoResult selctParm(@PathVariable Long id){
        return tbItemParamItemService.selectOne(id);

    }
}
