package com.ego.service;

import com.ego.commons.pojo.EasyUiDatagrid;
import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/8
 * @Description: com.ego.service
 * @version: 1.0
 */
public interface TbContentCategoryService {




    List<EasyUiTree> showAllTree(Long pid);


    EgoResult insert(TbContentCategory tcc);

    EgoResult updateName(TbContentCategory tcc);


    EgoResult delete(Long id);
}
