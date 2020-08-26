package com.ego.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.service.TBItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: liuxw
 * @Date: 2019/8/7
 * @Description: com.ego.controller
 * @version: 1.0
 */
@RestController
public class TBItemDesController {

    @Autowired
    private TBItemDescService tbItemDescService;

    @RequestMapping("/rest/item/query/item/desc/{id}")
    public EgoResult show(@PathVariable Long id){
        return tbItemDescService.findesc(id);
    }
}
