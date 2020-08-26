package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TBItemCatDubboService;
import com.ego.mapper.TbItemCatMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;
import com.ego.pojo.TbItemExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/7
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TBItemCatDubboServiceImpl implements TBItemCatDubboService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;




    @Override
    public List<TbItemCat> selectByPid(Long pid) {
        TbItemCatExample example = new TbItemCatExample();
        //where条件
        example.createCriteria().andParentIdEqualTo(pid);



        return tbItemCatMapper.selectByExample(example);
    }


    @Override
    public TbItemCat selectByid(Long id) {
        return tbItemCatMapper.selectByPrimaryKey(id);
    }
}
