package com.ego.dubbo.service;

import com.ego.commons.exception.EgoException;
import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/8
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbContentCategoryDubboService {

    List<TbContentCategory> showAllC(Long pid);


    Long insert(TbContentCategory tcc) throws EgoException;

    int updateName(TbContentCategory tcc);

    int delete(Long id) throws EgoException;

}
