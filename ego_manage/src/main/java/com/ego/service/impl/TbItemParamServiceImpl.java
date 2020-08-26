package com.ego.service.impl;

import com.ego.commons.pojo.EasyUiDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TBItemCatDubboService;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamChild;
import com.ego.pojo.TbItemParamExample;
import com.ego.service.TbItemParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: liuxw
 * @Date: 2019/8/7
 * @Description: com.ego.service.impl
 * @version: 1.0
 */
@Service
public class TbItemParamServiceImpl implements TbItemParamService {


    @Reference
    private TbItemParamDubboService tbItemParamDubboService;

    @Reference
    private TBItemCatDubboService tbItemCatDubboService;


    @Override
    public EasyUiDatagrid showItem(int page, int rows) {
        PageHelper.startPage(page, rows);
        EasyUiDatagrid datagrid = new EasyUiDatagrid();


        List<TbItemParam> list = tbItemParamDubboService.showItem(page, rows);
        List<TbItemParamChild>  list2= new ArrayList<>();
        for (TbItemParam tbI: list ) {

            TbItemParamChild tbItemParamChild = new TbItemParamChild();
            TbItemCat tbItemCat = tbItemCatDubboService.selectByid(tbI.getItemCatId());

            tbItemParamChild.setItemCatName(tbItemCat.getName());
            BeanUtils.copyProperties(tbI, tbItemParamChild);
            list2.add(tbItemParamChild);

        }

        PageInfo<TbItemParamChild> pi = new PageInfo<>(list2);

        datagrid.setRows(pi.getList());
        datagrid.setTotal(tbItemParamDubboService.showTotal());
        return datagrid ;
    }

    @Override
    public EgoResult findOne(Long id) {



           return EgoResult.ok(tbItemParamDubboService.selectByCid(id));


    }

    @Override
    public EgoResult save(TbItemParam tbItemParam) {
        int insert = tbItemParamDubboService.insert(tbItemParam);
        if (insert==1){
            return EgoResult.ok();
        }else{
            return EgoResult.error("添加失败！");
        }


    }


    @Override
    public EgoResult delete(Long id) {

        int i = tbItemParamDubboService.delete(id);
        if (i==1){
            return EgoResult.ok();
        }else{
            return EgoResult.error("删除失败！");
        }
    }
}
