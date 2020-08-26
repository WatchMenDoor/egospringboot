package com.ego.dubbo.service;

import com.ego.pojo.TbContent;

import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/8
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbContentDubboService {
    List<TbContent> showAllContent(Long cid);


    Long total(Long categoryid);

   //刪除
    int delete(Long id);


    //新增
    int save(TbContent tbContent);

    //修改
    int update(TbContent tbContent);




}
