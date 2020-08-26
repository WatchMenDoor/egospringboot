package com.ego.order.inteceptor;

import com.ego.commons.utils.CookieUtils;
import com.ego.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: liuxw
 * @Date: 2019/8/20
 * @Description: com.ego.inteceptor
 * @version: 1.0
 */
@Component
public class OrderInteceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Value("${mylogin.cookie.name}")
    private String cookieName;



    @Value("${passport.url.showLogin}")
    private String loginUrl;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否登录
        String cookieValue = CookieUtils.getCookieValue(request, cookieName, true);
        if (cookieValue!=null&&!cookieValue.equals("")){

            if (redisTemplate.hasKey(cookieValue)){
                redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<TbUser>(TbUser.class));
                Object o = redisTemplate.opsForValue().get(cookieValue);
                if (o!=null&&!o.equals("")){
                    return true;
                }
            }
        }


        response.sendRedirect(loginUrl);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
