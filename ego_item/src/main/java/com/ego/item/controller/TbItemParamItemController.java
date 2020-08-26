package com.ego.item.controller;


import com.ego.item.service.TbItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: liuxw
 * @Date: 2019/8/15
 * @Description: com.ego.item.controller
 * @version: 1.0
 */
@RestController
public class TbItemParamItemController {

    @Autowired
    private TbItemParamItemService tbItemParamItemService;

    @RequestMapping("/item/param/{id}.html")
    public String showDesc(@PathVariable Long id){

        return tbItemParamItemService.selectParamById(id);

    }


}
