package com.ego.portal.controller;

import com.ego.portal.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Auther: liuxw
 * @Date: 2019/8/12
 * @Description: com.ego.portal.controller
 * @version: 1.0
 */
@Controller
public class PageController {

    @Autowired
    private TbContentService tbContentService;


    @GetMapping("/")
    public String welcome(Model model){
        System.out.println("--------------------------执行了portal");

        model.addAttribute("ad1",tbContentService.showImg());

        return "index";
    }
}
