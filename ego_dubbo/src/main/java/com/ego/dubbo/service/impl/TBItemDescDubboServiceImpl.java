package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TBItemDescDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.pojo.TbItemDesc;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: liuxw
 * @Date: 2019/8/7
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TBItemDescDubboServiceImpl implements TBItemDescDubboService {

    @Autowired
    private TbItemDescMapper tbItemDescMapper;


    @Override
    public TbItemDesc selectById(Long id) {

        return tbItemDescMapper.selectByPrimaryKey(id);
    }
}
