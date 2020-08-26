package com.ego.passport.service.impl;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.UUID;

/**
 * @Auther: liuxw
 * @Date: 2019/8/16
 * @Description: com.ego.passport.service.impl
 * @version: 1.0
 */
@Service
public class TbUserServiceImpl implements TbUserService {

    @Reference
    private TbUserDubboService tbUserDubboService;
    @Autowired
    private RedisTemplate<String ,Object> redisTemplate;
    @Override
    public EgoResult login(TbUser user, HttpServletRequest request, HttpServletResponse response) {
        //密码加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        TbUser tbUser = tbUserDubboService.login(user);
        if(tbUser!=null){
            //下面所有代码相当于：session.setAttribute(token,tbUser"");
            //产生Cookie key：TT_TOKEN value：token
            String token = UUID.randomUUID().toString();
            CookieUtils.setCookie(request,response,"TT_TOKEN",token);
            //把用户信息放入到redis中
            //tbUser.setPassword("");
            redisTemplate.opsForValue().set(token,tbUser);
            return EgoResult.ok();
        }
        return EgoResult.error("登录失败");
    }

    @Override
    public EgoResult selectToken(String token) {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<TbUser>(TbUser.class));

        if (redisTemplate.hasKey(token)){
            TbUser o = (TbUser) redisTemplate.opsForValue().get(token);
            o.setPassword("");
            return EgoResult.ok(o);
        }
        return EgoResult.error("获取用户失败");
    }


    @Override
    public EgoResult logout(String token, HttpServletRequest request, HttpServletResponse response) {
        redisTemplate.delete(token);
        CookieUtils.deleteCookie(request,response,token);
        return EgoResult.ok();
    }


    @Override
    public EgoResult checkName(String username) {
        TbUser tbUser = tbUserDubboService.checkUser(username, null);
        if (tbUser!=null){
            return EgoResult.error("该用户名已存在");
        }
        return EgoResult.ok("用户名可用");
    }

    @Override
    public EgoResult checkPhone(String phone) {
        TbUser tbUser = tbUserDubboService.checkUser(null, phone);
        if (tbUser!=null){
            return EgoResult.error("该手机号已存在");
        }
        return EgoResult.ok("手机名可用");
    }

    @Override
    public EgoResult save(TbUser tbUser) {
        Date date = new Date();
        tbUser.setCreated(date);
        tbUser.setUpdated(date);
        tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
        int i = tbUserDubboService.insert(tbUser);
        if (i==1){
            return EgoResult.ok();
        }
        return EgoResult.error("注册失败");
    }
}
