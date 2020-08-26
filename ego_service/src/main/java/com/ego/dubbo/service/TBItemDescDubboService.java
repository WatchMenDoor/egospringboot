package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

/**
 * @Auther: liuxw
 * @Date: 2019/8/7
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TBItemDescDubboService {

    TbItemDesc selectById(Long id);
}
