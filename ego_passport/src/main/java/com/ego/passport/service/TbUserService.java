package com.ego.passport.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: liuxw
 * @Date: 2019/8/16
 * @Description: com.ego.passport.service
 * @version: 1.0
 */
public interface TbUserService {

    EgoResult login(TbUser user, HttpServletRequest request, HttpServletResponse response);

    EgoResult selectToken(String token);

    EgoResult logout(String token,HttpServletRequest request, HttpServletResponse response);

    EgoResult checkName(String username);
    EgoResult checkPhone(String phone);

    EgoResult save(TbUser tbUser);
}
