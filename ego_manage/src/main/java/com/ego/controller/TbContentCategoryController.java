package com.ego.controller;

import com.ego.commons.pojo.EasyUiDatagrid;
import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;
import com.ego.service.TbContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/8
 * @Description: com.ego.controller
 * @version: 1.0
 */
@RestController
public class TbContentCategoryController {
    @Autowired
    private TbContentCategoryService tbContentCategoryService;



    @GetMapping("/content/category/list")
    public List<EasyUiTree> showAll(@RequestParam(defaultValue = "0") Long id){
        return tbContentCategoryService.showAllTree(id);

    }

    @PostMapping("/content/category/create")
    public EgoResult insert(TbContentCategory tcc){
        return tbContentCategoryService.insert(tcc);

    }


    @PostMapping("/content/category/update")
    public EgoResult updateName(TbContentCategory tcc){
        return tbContentCategoryService.updateName(tcc);

    }

    @PostMapping("/content/category/delete/")
    public EgoResult deleteName(Long id){
        return tbContentCategoryService.delete(id);

    }
}
