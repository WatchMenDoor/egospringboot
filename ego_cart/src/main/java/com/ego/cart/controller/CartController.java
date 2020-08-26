package com.ego.cart.controller;


import com.ego.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: liuxw
 * @Date: 2019/8/17
 * @Description: com.ego.cat.controller
 * @version: 1.0
 */
@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("/cart/add/{id}.html")
    public String  addCat(@PathVariable Long id, int num, HttpServletRequest request, HttpServletResponse response){

        cartService.addCart(id,num,response,request);
        return "cartSuccess";

    }

    @GetMapping("/cart/cart.html")
    public String showCart(HttpServletRequest request,HttpServletResponse response, Model model){

        model.addAttribute("cartList", cartService.getCart(request,response));
        return "cart";

    }
}
