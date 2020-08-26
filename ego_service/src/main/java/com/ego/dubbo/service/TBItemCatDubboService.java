package com.ego.dubbo.service;

import com.ego.pojo.TbItemCat;

import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/7
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TBItemCatDubboService {

    List<TbItemCat> selectByPid(Long pid);

    TbItemCat selectByid(Long id);
}


