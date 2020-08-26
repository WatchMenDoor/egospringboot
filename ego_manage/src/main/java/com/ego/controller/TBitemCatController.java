package com.ego.controller;

import com.ego.service.TBItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TBitemCatController {

    @Autowired
    private TBItemCatService tbItemCatService;


    @RequestMapping("/item/cat/list")
    public Object showItemCat(@RequestParam(defaultValue = "0") Long id){
        return tbItemCatService.showItem(id);

    }


}
