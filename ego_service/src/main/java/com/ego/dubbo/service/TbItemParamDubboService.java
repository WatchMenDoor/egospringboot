package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUiDatagrid;
import com.ego.pojo.TbItemParam;

import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/7
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbItemParamDubboService {

    List<TbItemParam> showItem(int page, int rows);

    Long showTotal();

    TbItemParam selectByCid(Long cid);


    int insert(TbItemParam tbItemParam);

    int delete(Long id);

}
