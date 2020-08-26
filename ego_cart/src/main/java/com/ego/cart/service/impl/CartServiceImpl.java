package com.ego.cart.service.impl;

import com.ego.commons.pojo.CartEntity;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.SearchResultEntity;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/19
 * @Description: com.ego.cart.service.impl
 * @version: 1.0
 */
@Service
public class CartServiceImpl implements CartService {

    @Value("${mylogin.cookie.name}")
    private String cookieName;
    @Value("${passport.url.getUserInfo}")
    private String passportUrl;
    @Value("${cart.cookiename}")
    private String cartCookieName;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Value("${myredis.itemkey}")
    private String redisKey;
    @Value("${cart.usercart}")
    private String userCartKey;
    @Reference
    private TbItemDubboService tbItemDubboService;

    @Override
    public boolean addCart(Long id, int num, HttpServletResponse response, HttpServletRequest request) {


        //用户未登录
        //根据id取出商品信息，把商品信息放入到cookie中

        /*取出商品信息*/
        String key =redisKey+":"+id;//redis中存储的key格式
        SearchResultEntity sre = null;//？
        if (!redisTemplate.hasKey(key)){//redis中没有存储商品信息--防止信息出错和业务没有大关系

            sre = new SearchResultEntity();
            //从数据库中取值
            TbItem item = tbItemDubboService.selectByid(id);
            sre.setId(item.getId()+"");
            sre.setTitle(item.getTitle());
            sre.setSellPoint(item.getSellPoint());
            sre.setPrice(item.getPrice());
            String image = item.getImage();
            sre.setImages(image!=null&&!image.equals("")?image.split(","):new String[0]);
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<SearchResultEntity>(SearchResultEntity.class));
            redisTemplate.opsForValue().set(key, sre);
        }
        if (sre==null){//redis中存有商品信息
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<SearchResultEntity>(SearchResultEntity.class));
            sre = (SearchResultEntity) redisTemplate.opsForValue().get(key);

        }


        //判断用户是否已经登录
        String cookieValue = CookieUtils.getCookieValue(request, cookieName);
        if (cookieValue!=null&&!cookieValue.equals("")){
            //cookie不为空，（已经登录或非第一次访问）
            String result = HttpClientUtil.doGet(passportUrl + cookieValue);
            EgoResult er = JsonUtils.jsonToPojo(result, EgoResult.class);

            if (er.getStatus()==200){
                //用户已经登录
                /*cookie中购物车的数据*/
                //先把临时购物车合并到用户购物车中，用户购物车在redis中，临时购物车在cookie中
                String jsonCartCookieValue = CookieUtils.getCookieValue(request, cartCookieName, true);
                List<CartEntity> listTemplateCart = null;
                //临时购物车中的数据
                if (jsonCartCookieValue!=null&&!jsonCartCookieValue.equals("")){
                    //临时购物车中的数据
                     listTemplateCart = JsonUtils.jsonToList(jsonCartCookieValue, CartEntity.class);

                }


                //用户购物车中的数据
                String userKey =userCartKey+":"+((LinkedHashMap)er.getData()).get("id");
                //如果用户没有购物车-直接把cookie的加入
                if (!redisTemplate.hasKey(userKey)){
                    /*不应该是复制list吗？*/
                    List<CartEntity> list = new ArrayList<>();
                    CartEntity ce = new CartEntity();
                    BeanUtils.copyProperties(sre, ce);
                    ce.setNum(num);
                    list.add(ce);
                    /*这点是不是不需要*/
                    if (listTemplateCart!=null){//用户购物车中数据不为0
                        for (CartEntity temCart:listTemplateCart) {//遍历cookie中的购物车和redis中的购物车是否有相同的商品
                            boolean isExists = false;
                            for (CartEntity cart:list){
                                if (cart.getId().equals(temCart.getId())) {//有相同商品，就将数量相加，并停止循环
                                    cart.setNum(cart.getNum() + temCart.getNum());
                                    isExists = true;
                                    break;
                                }
                            }

                            if (!isExists){//没有相同的商品，直接添加商品
                                list.add(temCart);
                            }

                        }

                    }
                    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<List>(List.class));
                    redisTemplate.opsForValue().set(userKey,list);
                }else {//用户已经有了自己的购物车--自己的加上cookie里面的

                    redisTemplate.setValueSerializer(new StringRedisSerializer());
                    //用户自己的购物车
                    String jsonValue = redisTemplate.opsForValue().get(userKey).toString();
                    List<CartEntity> list = JsonUtils.jsonToList(jsonValue, CartEntity.class);
                    /*购物车之前也可能有同样的产品*/
                    boolean ishasElement = false;
                    for (CartEntity ce : list) {
                        if (ce.getId().equals(id + "")) {
                            //已有的商品id等于穿过来的id
                            ce.setNum(ce.getNum() + num);
                            ishasElement = true;
                            break;
                        }
                    }
                    //如果购物车没有这个商品，把商品添加到购物车即可
                    if (!ishasElement) {
                        CartEntity ce = new CartEntity();
                        BeanUtils.copyProperties(sre, ce);
                        ce.setNum(num);
                        list.add(ce);
                    }

                    if (listTemplateCart != null) {
                        for (CartEntity temCart : listTemplateCart) {
                            boolean isExists = false;
                            for (CartEntity cart : list) {
                                if (cart.getId().equals(temCart.getId())) {
                                    cart.setNum(cart.getNum() + temCart.getNum());
                                    isExists = true;
                                    break;
                                }
                            }
                            if (!isExists) {
                                list.add(temCart);
                            }

                        }

                    }
                    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<List>(List.class));
                    redisTemplate.opsForValue().set(userKey, list);

                }//删除临时购物车
                CookieUtils.deleteCookie(request,response,cartCookieName);
                return true;

            }
        }

        //操作临时购物车,临时购物车中数据都放入到CartEntity中
        String cartCookieValue = CookieUtils.getCookieValue(request, cartCookieName, true);
        //如果临时购物车有商品，把商品取出
        if (cartCookieValue!=null&&!cartCookieValue.equals("")){
            //临时购物车中有商品，取出
            List<CartEntity> list = JsonUtils.jsonToList(cartCookieValue, CartEntity.class);
            //如果购物车有这个商品，对数量进行增加
            boolean ishasElement = false;
            for (CartEntity ce :list){
                if (ce.getId().equals(id+"")){
                    //已有的商品id等于穿过来的id
                    ce.setNum(ce.getNum()+num);
                    ishasElement = true;
                    break;
                }
            }
            //如果购物车没有这个商品，把商品添加到购物车即可
            if (!ishasElement){
                CartEntity ce = new CartEntity();
                BeanUtils.copyProperties(sre, ce);
                ce.setNum(num);
                list.add(ce);
            }
            //临时购物车，放到cookie中
            CookieUtils.setCookie(request, response, cartCookieName,JsonUtils.objectToJson(list),true);
        }else{
            //目前没有临时购物车
            List<CartEntity> list =new ArrayList<>();
            CartEntity ce = new CartEntity();
            BeanUtils.copyProperties(sre, ce);
            ce.setNum(num);
            list.add(ce);
            CookieUtils.setCookie(request, response, cartCookieName,JsonUtils.objectToJson(list),true);
        }
        return true;
    }


    @Override
    public List<CartEntity> getCart(HttpServletRequest request,HttpServletResponse response) {
        //判断用户是否已经登录
        //如果没有登录，从cookie取
        //登录，从redis中取
        String cookieValue = CookieUtils.getCookieValue(request, cookieName);
        if (cookieValue != null && !cookieValue.equals("")) {
            //cookie不为空
            String result = HttpClientUtil.doGet(passportUrl + cookieValue);
            EgoResult er = JsonUtils.jsonToPojo(result, EgoResult.class);
            //判断是否登录
            if (er.getStatus() == 200) {//已经登录
                String userRedisKey = userCartKey+":"+((LinkedHashMap<String,Object>)er.getData()).get("id");
                redisTemplate.setValueSerializer(new StringRedisSerializer());
                List<CartEntity> list = null;

                if (redisTemplate.hasKey(userRedisKey)){
                    String json = redisTemplate.opsForValue().get(userRedisKey).toString();
                    list= JsonUtils.jsonToList(json, CartEntity.class);
                }else{
                    list= new ArrayList<>();

                }
                //合并临时购物车和用户购物车
                String json = CookieUtils.getCookieValue(request, cartCookieName, true);
                if (json!=null&&!json.equals("")){

                    List<CartEntity> listTemp = JsonUtils.jsonToList(json, CartEntity.class);

                    for (CartEntity ce:listTemp){
                        boolean isExits=false;
                        for (CartEntity cartEntity: list) {

                            if (cartEntity.getId().equals(ce.getId())){
                                cartEntity.setNum(cartEntity.getNum()+ce.getNum());
                                isExits=true;
                                break;
                            }
                        }
                        if (!isExits){
                            list.add(ce);
                        }
                    }
                    CookieUtils.deleteCookie(request,response, cartCookieName);
                }
                return list;
            }

        }
         /*没登录，或不是第一次添加，从cookie中查询*/
        String json = CookieUtils.getCookieValue(request, cartCookieName, true);
        if (json!=null&&!json.equals("")){
            return JsonUtils.jsonToList(json, CartEntity.class);

        }

        return new ArrayList<>();
    }
}
