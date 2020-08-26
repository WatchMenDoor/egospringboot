package com.ego.service.impl;

import com.ego.commons.exception.EgoException;
import com.ego.commons.pojo.EasyUiDatagrid;
import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.pojo.TbContentCategory;
import com.ego.service.TbContentCategoryService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/8
 * @Description: com.ego.service.impl
 * @version: 1.0
 */
@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {


    @Reference
    private TbContentCategoryDubboService tbContentCategoryDubboService;

    @Override
    public List<EasyUiTree> showAllTree(Long pid) {
        List<TbContentCategory> list = tbContentCategoryDubboService.showAllC(pid);
        List<EasyUiTree> treeList = new ArrayList<>();
        for (TbContentCategory l:list) {
            EasyUiTree tree = new EasyUiTree();
            tree.setState(l.getIsParent()?"closed":"open");
            tree.setId(l.getId());
            tree.setText(l.getName());
            treeList.add(tree);


        }
        return treeList;
    }


    @Override
    public EgoResult insert(TbContentCategory tcc) {
        tcc.setIsParent(false);
        tcc.setStatus(1);
        tcc.setSortOrder(1);
        Long index = null;
        try {
            index = tbContentCategoryDubboService.insert(tcc);
        } catch (EgoException e) {
            e.printStackTrace();
            index = 0L;
        }
        if (!index.equals(0L)){
            tcc.setId(index);
            return EgoResult.ok(tcc);
        }
        return EgoResult.error("新增失敗");
    }


    //修改名字

    @Override
    public EgoResult updateName(TbContentCategory tcc) {
        int i = tbContentCategoryDubboService.updateName(tcc);
        if (i==1){
            return EgoResult.ok();
        }
        return EgoResult.error("重命名失敗");
    }

    //刪除
    @Override
    public EgoResult delete(Long id) {
        int delete = tbContentCategoryDubboService.delete(id);
        if (delete==1){
            return EgoResult.ok();
        }
        return EgoResult.error("刪除失敗");
    }
}
