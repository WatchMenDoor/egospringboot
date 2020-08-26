package com.ego.item.controller;

import com.ego.item.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Auther: liuxw
 * @Date: 2019/8/12
 * @Description: com.ego.item.controller
 * @version: 1.0
 */
@RestController
public class ItemCatController {


    @Autowired
    private ItemCatService itemCatService;

    @CrossOrigin
    @GetMapping("/rest/itemcat/all")
    public Map<String,Object> showMenu(){
        return itemCatService.showMenu();
    }


}
