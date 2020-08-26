package com.ego.service.impl;

import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TBItemDescDubboService;
import com.ego.pojo.TbItemDesc;
import com.ego.service.TBItemDescService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * @Auther: liuxw
 * @Date: 2019/8/7
 * @Description: com.ego.service.impl
 * @version: 1.0
 */
@Service
public class TBItemDescServiceImpl implements TBItemDescService {

    @Reference
    private TBItemDescDubboService tbItemDescDubboService;


    @Override
    public EgoResult findesc(Long id) {
        TbItemDesc tbItemDesc = tbItemDescDubboService.selectById(id);
        EgoResult egoResult = new EgoResult();
        egoResult.setStatus(200);
        egoResult.setData(tbItemDesc);
        return egoResult;
    }
}
