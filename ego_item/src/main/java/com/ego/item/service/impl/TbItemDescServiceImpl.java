package com.ego.item.service.impl;

import com.ego.dubbo.service.TBItemDescDubboService;
import com.ego.item.service.TbItemDescService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: liuxw
 * @Date: 2019/8/15
 * @Description: com.ego.item.service.impl
 * @version: 1.0
 */
@Service
public class TbItemDescServiceImpl implements TbItemDescService {

    @Reference
    private TBItemDescDubboService tbItemDescDubboService;


    @Override
    public String showDesc(Long id) {
        return tbItemDescDubboService.selectById(id).getItemDesc();
    }
}
