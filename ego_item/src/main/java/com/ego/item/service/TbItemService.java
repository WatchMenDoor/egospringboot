package com.ego.item.service;

import com.ego.commons.pojo.SearchResultEntity;
import com.ego.pojo.TbItem;

/**
 * @Auther: liuxw
 * @Date: 2019/8/15
 * @Description: com.ego.item.service
 * @version: 1.0
 */
public interface TbItemService {
     SearchResultEntity selectItem(Long id);
}
