package com.ego.service;

import com.ego.commons.pojo.EasyUiDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContent;

/**
 * @Auther: liuxw
 * @Date: 2019/8/8
 * @Description: com.ego.service
 * @version: 1.0
 */
public interface TbContentService {
    EasyUiDatagrid showAllContent(Long cid,int page,int rows);

    EgoResult delete(Long id);


    EgoResult save(TbContent tbContent);

    EgoResult update(TbContent tbContent);

}
