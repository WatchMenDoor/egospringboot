package com.ego.cart.service;

import com.ego.commons.pojo.CartEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/19
 * @Description: com.ego.cart.service
 * @version: 1.0
 */
public interface CartService {
    boolean addCart(Long id, int num, HttpServletResponse response, HttpServletRequest request);

    List<CartEntity> getCart(HttpServletRequest request,HttpServletResponse response);



}
