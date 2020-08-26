package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbContentDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/8
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbContentDubboServiceImpl implements TbContentDubboService {

    @Autowired
    TbContentMapper tbContentMapper;

    @Override
    public List<TbContent> showAllContent(Long cid) {
        TbContentExample example = new TbContentExample();
        if (cid!=0){
            example.createCriteria().andCategoryIdEqualTo(cid);
        }

        return tbContentMapper.selectByExample(example);
    }

    @Override
    public int delete(Long id) {

        return tbContentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Long total(Long categoryid) {
        TbContentExample example=new TbContentExample();
        if (categoryid!=0){
            example.createCriteria().andCategoryIdEqualTo(categoryid);
        }
        return tbContentMapper.countByExample(example);
    }

    @Override
    public int save(TbContent tbContent) {
        Date date = new Date();
        tbContent.setCreated(date);
        tbContent.setUpdated(date);
        return tbContentMapper.insertSelective(tbContent);
    }

    @Override
    public int update(TbContent tbContent) {
        return tbContentMapper.updateByPrimaryKeyWithBLOBs(tbContent);
    }
}
