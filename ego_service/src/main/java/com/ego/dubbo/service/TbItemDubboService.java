package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUiDatagrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/6
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbItemDubboService {

EasyUiDatagrid showItem(int page,int rows);

int updateBy(String ids,Byte status);


int update(TbItem tbItem, TbItemDesc tbItemDesc,TbItemParamItem tbItemParamItem);

    long insertItem(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem tbItemParamItem);


//solr数据初始化
    List<TbItem> selectAll();

    //查询商品详情
    TbItem selectByid(Long id);
}
