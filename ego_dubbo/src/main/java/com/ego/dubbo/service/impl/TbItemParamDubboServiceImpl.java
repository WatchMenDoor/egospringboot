package com.ego.dubbo.service.impl;

import com.ego.commons.pojo.EasyUiDatagrid;
import com.ego.dubbo.service.TbItemParamDubboService;

import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/7
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {

    @Autowired
    private TbItemParamMapper tbItemParamMapper;


    @Override
    public List<TbItemParam> showItem(int page,int rows) {

        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
        return list;
    }

    @Override
    public Long showTotal() {

        return tbItemParamMapper.countByExample(new TbItemParamExample());
    }


    @Override
    public TbItemParam selectByCid(Long cid) {
        TbItemParamExample tbItemParamExample = new TbItemParamExample();
        tbItemParamExample.createCriteria().andItemCatIdEqualTo(cid);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(tbItemParamExample);
        if (list!=null&&list.size()>0){
            return list.get(0);
        }

        return null;
    }


    @Override
    public int insert(TbItemParam tbItemParam) {
        Date date = new Date();
        tbItemParam.setCreated(date);
        tbItemParam.setUpdated(date);
        return tbItemParamMapper.insert(tbItemParam);
    }


    @Override
    public int delete(Long id) {

        return tbItemParamMapper.deleteByPrimaryKey(id);
    }
}
