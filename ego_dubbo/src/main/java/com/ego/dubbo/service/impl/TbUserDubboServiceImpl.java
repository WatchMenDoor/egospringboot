package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbUserDubboService;
import com.ego.mapper.TbUserMapper;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/16
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbUserDubboServiceImpl implements TbUserDubboService {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Override
    public TbUser login(TbUser tbUser) {
        TbUserExample example = new TbUserExample();
        example.createCriteria().andUsernameEqualTo(tbUser.getUsername()).andPasswordEqualTo(tbUser.getPassword());
        List<TbUser> list = tbUserMapper.selectByExample(example);
        if (list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }


    @Override
    public TbUser checkUser(String username, String phone) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        if (username!=null&&!username.equals("")){
            criteria = criteria.andUsernameEqualTo(username);
        }
        if (phone!=null&&!phone.equals("")){
            criteria = criteria.andPhoneEqualTo(phone);
        }
        List<TbUser> list = tbUserMapper.selectByExample(example);
        if (list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;

    }

    @Override
    public int insert(TbUser tbUser) {
        return tbUserMapper.insertSelective(tbUser);
    }
}
