package com.ego.search.controller;

import com.ego.search.pojo.SolrEntity;
import com.ego.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/14
 * @Description: com.ego.search.controller
 * @version: 1.0
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/init")
    @ResponseBody
    public String init(){
        long start = System.currentTimeMillis();
        searchService.init();
        long stop = System.currentTimeMillis();
        return "耗时："+((stop-start)/1000)+"秒";

    }

    @RequestMapping("/search.html")
    public  String show(String q, @RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "10")int rows, Model model){
        model.addAllAttributes(searchService.search(q, page, rows));

        return "search";
    }

    @RequestMapping("/save")
    @ResponseBody
    public int save(SolrEntity se){
        return searchService.save(se);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public int save(@RequestBody List<String> ids){
        return searchService.delete(ids);
    }

}
