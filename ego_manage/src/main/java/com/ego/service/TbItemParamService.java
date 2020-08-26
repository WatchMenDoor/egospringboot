package com.ego.service;

import com.ego.commons.pojo.EasyUiDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemParam;

import java.util.Map;

/**
 * @Auther: liuxw
 * @Date: 2019/8/7
 * @Description: com.ego.service
 * @version: 1.0
 */
public interface TbItemParamService {

    EasyUiDatagrid showItem(int page,int rows);

    EgoResult findOne(Long id);

    EgoResult save(TbItemParam tbItemParam);

    //删除一个
    EgoResult delete(Long id);
}
