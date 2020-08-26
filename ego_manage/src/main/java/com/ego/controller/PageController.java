package com.ego.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: liuxw
 * @Date: 2019/8/6
 * @Description: com.ego.controller
 * @version: 1.0
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String welcome(){


        return "index";
    }

    @RequestMapping("{page}")
    public String showPage(@PathVariable String page){
        return page;

    }

    @RequestMapping("/rest/page/item-edit")
    public String showPage(){


        return "item-edit";

    }




}
