package com.ego.item.controller;

import com.ego.item.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: liuxw
 * @Date: 2019/8/15
 * @Description: com.ego.item.controller
 * @version: 1.0
 */
@Controller
public class ItemController {
    @Autowired
    private TbItemService itemService;


    @RequestMapping("/item/{id}.html")
    public String showItemdetails(@PathVariable Long id, Model model){
        model.addAttribute("item",itemService.selectItem(id));
        return "item";

    }
}
