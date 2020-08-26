package com.ego.service;

import com.ego.commons.pojo.EasyUiDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItem;

/**
 * @Auther: liuxw
 * @Date: 2019/8/6
 * @Description: com.ego.service
 * @version: 1.0
 */
public interface TbItemService {
    EasyUiDatagrid show(int page,int rows);

    EgoResult updateStatus(String ids,Byte status);


    EgoResult update(TbItem item,String desc,String itemParams,Long itemParamId);


    EgoResult insert(TbItem item,String desc,String itemParams);



}
