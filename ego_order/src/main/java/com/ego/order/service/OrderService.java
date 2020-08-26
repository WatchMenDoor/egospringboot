package com.ego.order.service;

import com.ego.order.pojo.OrderParam;
import com.ego.order.pojo.OrderEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Auther: liuxw
 * @Date: 2019/8/20
 * @Description: com.ego.service
 * @version: 1.0
 */
public interface OrderService {

    List<OrderEntity> showOrderCart(List<Long> ids, HttpServletRequest request);

    Map<String,Object> create(OrderParam orderParam, HttpServletRequest request );
}
