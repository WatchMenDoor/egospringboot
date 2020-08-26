package com.ego.search.service;

import com.ego.search.pojo.SolrEntity;

import java.util.List;
import java.util.Map;

/**
 * @Auther: liuxw
 * @Date: 2019/8/14
 * @Description: com.ego.search.service
 * @version: 1.0
 */
public interface SearchService {
    void init();

    Map<String,Object> search(String q,int page,int rows);

    //新增
    int save(SolrEntity se);

    //删除
    int delete(List<String> ids);
}
