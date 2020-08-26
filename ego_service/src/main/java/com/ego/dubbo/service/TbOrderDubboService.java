package com.ego.dubbo.service;

import com.ego.commons.exception.ItemNumNotEnoughException;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/20
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbOrderDubboService {
    String save(TbOrder tbOrder, List<TbOrderItem> list, TbOrderShipping tbOrderShipping) throws ItemNumNotEnoughException;
}
