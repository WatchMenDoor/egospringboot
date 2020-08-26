package com.ego.dubbo.service;

import com.ego.pojo.TbUser;

/**
 * @Auther: liuxw
 * @Date: 2019/8/16
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbUserDubboService {
    TbUser login(TbUser tbUser);

    TbUser checkUser(String username,String phone);

    int insert(TbUser tbUser);
}
