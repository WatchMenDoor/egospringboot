package com.ego.controller;

import com.ego.commons.pojo.EasyUiDatagrid;
import com.ego.pojo.TbContent;
import com.ego.service.TbContentCategoryService;
import com.ego.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: liuxw
 * @Date: 2019/8/8
 * @Description: com.ego.controller
 * @version: 1.0
 */
@RestController
public class TbContentController {

    @Autowired
    private TbContentService tbContentService;

    @GetMapping("/content/query/list")
    public EasyUiDatagrid showAll(Long categoryId, int page,int rows){
        return tbContentService.showAllContent(categoryId,page, rows);

    }


    @PostMapping("/content/delete")
    public Object delete(Long ids){
        return tbContentService.delete(ids);

    }

    @PostMapping("/content/save")
    public Object save(TbContent tbContent){
        return tbContentService.save(tbContent);

    }

    @PostMapping("/rest/content/edit")
    public Object update(TbContent tbContent){
        return tbContentService.update(tbContent);

    }
}
