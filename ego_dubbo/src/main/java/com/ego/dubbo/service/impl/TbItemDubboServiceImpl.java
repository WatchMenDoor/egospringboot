package com.ego.dubbo.service.impl;

import com.ego.commons.pojo.EasyUiDatagrid;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemExample;
import com.ego.pojo.TbItemParamItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/6
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbItemDubboServiceImpl implements TbItemDubboService{

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Override
    public EasyUiDatagrid showItem(int page, int rows) {
        PageHelper.startPage(page, rows);
        List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
        PageInfo<TbItem> pi = new PageInfo<>(list);
        EasyUiDatagrid datagrid = new EasyUiDatagrid();
        datagrid.setRows(pi.getList());
        datagrid.setTotal(pi.getTotal());
        return datagrid;
    }


    @Override
    @Transactional
    public int updateBy(String ids, Byte status) {

        String[] split = ids.split(",");
        Date date = new Date();
        int index=0;
        for (String str: split) {
            TbItem tbItem =new TbItem();
            tbItem.setId(Long.parseLong(str));
            tbItem.setStatus(status);
            tbItem.setUpdated(date);
            index+=tbItemMapper.updateByPrimaryKeySelective(tbItem);
        }
        if (index==split.length){
            return 1;
        }
        return 0;
    }


    @Override
    @Transactional
        public int update(TbItem tbItem, TbItemDesc tbItemDesc,TbItemParamItem tbItemParamItem) {

        Date date = new Date();
        tbItem.setUpdated(date);
        tbItemDesc.setUpdated(date);
        tbItemParamItem.setUpdated(date);
        int i = tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
        i+=tbItemMapper.updateByPrimaryKeySelective(tbItem);
        i+=tbItemParamItemMapper.updateByPrimaryKeySelective(tbItemParamItem);
        if (i==3){
            return 1;
        }
        return 0;
    }


    @Override
    @Transactional
    public long insertItem(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem tbItemParamItem) {
        long itemId = IDUtils.genItemId();
        tbItem.setId(itemId);
        tbItemDesc.setItemId(itemId);
        tbItemParamItem.setItemId(itemId);
       Date date = new Date();
       tbItem.setUpdated(date);
       tbItem.setCreated(date);
       tbItemDesc.setUpdated(date);
       tbItemDesc.setCreated(date);
       tbItemParamItem.setCreated(date);
       tbItemParamItem.setUpdated(date);
        int i = tbItemMapper.insertSelective(tbItem);
        i+=tbItemDescMapper.insertSelective(tbItemDesc);
        i+=tbItemParamItemMapper.insertSelective(tbItemParamItem);
        if (i==3){
            return itemId;
        }
        return 0;
    }

    //solr的初始化
    @Override
    public List<TbItem> selectAll() {
        return tbItemMapper.selectByExample(new TbItemExample());
    }

    //solr的商品详情
    @Override
    public TbItem selectByid(Long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }
}
