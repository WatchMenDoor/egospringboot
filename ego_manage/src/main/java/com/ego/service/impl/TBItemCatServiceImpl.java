package com.ego.service.impl;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.dubbo.service.TBItemCatDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItemCat;
import com.ego.service.TBItemCatService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/7
 * @Description: com.ego.service.impl
 * @version: 1.0
 */
@Service
public class TBItemCatServiceImpl implements TBItemCatService {

    @Reference
    private TBItemCatDubboService tbItemCatDubboService;


    @Override
    public List<EasyUiTree> showItem(Long id) {

        List<TbItemCat> list = tbItemCatDubboService.selectByPid(id);
        List<EasyUiTree> treeList = new ArrayList<>();
        for (TbItemCat cat:list){
            EasyUiTree tree = new EasyUiTree();
            tree.setId(cat.getId());
            tree.setText(cat.getName());
            tree.setState(cat.getIsParent()?"closed":"open");
            treeList.add(tree);
        }


        return treeList;
    }
}
