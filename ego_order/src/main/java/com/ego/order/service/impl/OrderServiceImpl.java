package com.ego.order.service.impl;

import com.ego.commons.exception.ItemNumNotEnoughException;
import com.ego.commons.pojo.CartEntity;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.order.pojo.OrderEntity;
import com.ego.order.pojo.OrderParam;
import com.ego.pojo.*;
import com.ego.order.service.OrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Auther: liuxw
 * @Date: 2019/8/20
 * @Description: com.ego.service.impl
 * @version: 1.0
 */
@Service
public class  OrderServiceImpl implements OrderService {


    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Value("${cart.usercart}")
    private String userCartKey;

    @Value("${mylogin.cookie.name}")
    private String cookieName;
    @Reference
    private TbItemDubboService tbItemDubboService;
    @Reference
    private TbOrderDubboService tbOrderDubboService;

    @Override
    public List<OrderEntity> showOrderCart(List<Long> ids, HttpServletRequest request) {
        List<OrderEntity> listOrder = new ArrayList<>();
        //取出购物车信息
        String cookieValue = CookieUtils.getCookieValue(request, cookieName, true);
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        String json = redisTemplate.opsForValue().get(cookieValue).toString();
        TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);

        String key = userCartKey+":"+tbUser.getId();
        String userCartJson = redisTemplate.opsForValue().get(key).toString();
        //购物车中所有商品
        List<OrderEntity> listCart = JsonUtils.jsonToList(userCartJson,OrderEntity.class);
        for (Long id:ids){
            for (OrderEntity oe:listCart){
                if (oe.getId().equals(id+"")){
                    //判断商品数量是否够用
                    Integer num = tbItemDubboService.selectByid(id).getNum();
                    if ((int)num>=oe.getNum()){
                        oe.setEnough(true);
                    }else{
                        oe.setEnough(false);
                    }
                    listOrder.add(oe);
                    break;
                }
            }
        }
        return listOrder;
    }


    @Override
    public Map<String, Object> create(OrderParam orderParam, HttpServletRequest request) {
        Map<String,Object> map=new HashMap<>();
        TbOrder tbOrder = new TbOrder();
        tbOrder.setPayment(orderParam.getPayment());
        tbOrder.setPaymentType(orderParam.getPaymentType());
        String orderId = null;
        try {
            orderId = tbOrderDubboService.save(tbOrder, orderParam.getOrderItems(), orderParam.getOrderShipping());
        } catch (ItemNumNotEnoughException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (orderId!=null){
            //订单信息不为空
         map.put("orderId", orderId);
         map.put("payment", orderParam.getPayment());

            Calendar curr = Calendar.getInstance();
            //11点，9点查询
            Calendar clone11 = (Calendar)curr.clone();
            Calendar clone22 = (Calendar)curr.clone();
            clone11.set(Calendar.HOUR_OF_DAY, 11);
            clone11.set(Calendar.MINUTE, 0);
            clone11.set(Calendar.SECOND, 0);

            clone11.set(Calendar.HOUR_OF_DAY, 21);
            clone11.set(Calendar.MINUTE, 0);
            clone11.set(Calendar.SECOND, 0);

            if (curr.compareTo(clone11)<=0){
              //当前时间小于11点，当天送达

           }else if (curr.compareTo(clone11)==1&&curr.compareTo(clone22)<=0){
              curr.add(Calendar.DAY_OF_MONTH, 1);

            }else if(curr.compareTo(clone22)==1){
                curr.add(Calendar.DAY_OF_MONTH, 1);
            }
            map.put("date", curr.getTime());



            //删除购物车中的商品
           //先从redis中购物车中取出信息
            String cookieValue = CookieUtils.getCookieValue(request, cookieName, true);
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            String json = redisTemplate.opsForValue().get(cookieValue).toString();
            TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
            String key = userCartKey+":"+tbUser.getId();
            String userCartJson = redisTemplate.opsForValue().get(key).toString();
            //购物车中所有商品
            List<CartEntity> listCart = JsonUtils.jsonToList(userCartJson,CartEntity.class);
            //循环遍历，找到已经添加到购物车中的数据
            for (TbOrderItem toi:orderParam.getOrderItems()){
                for (CartEntity ce: listCart) {
                    if (toi.getItemId().equals(ce.getId())){
                        //购物车和订单中商品一样
                        listCart.remove(ce);
                        break;
                    }
                }
            }
            //删除后重新把数据放到redis里
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<List>(List.class));
            redisTemplate.opsForValue().set(key, listCart);
            return map;
        }
        return null;
    }
}
