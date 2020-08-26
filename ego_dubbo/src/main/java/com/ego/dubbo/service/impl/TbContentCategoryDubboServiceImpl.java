package com.ego.dubbo.service.impl;

import com.ego.commons.exception.EgoException;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/8
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {

    @Autowired
    TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<TbContentCategory> showAllC(Long pid) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.setOrderByClause("sort_order");
        example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
        return tbContentCategoryMapper.selectByExample(example);
    }


    @Override
    @Transactional
    public Long insert(TbContentCategory tcc)throws EgoException{
        Date date = new Date();
        tcc.setCreated(date);
        tcc.setUpdated(date);
        //新增，返回的是ID
        int i = tbContentCategoryMapper.insertSelectiveReturnId(tcc);
        //查詢父類是否是父節點
        TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(tcc.getParentId());
        if (!parent.getIsParent()){
            TbContentCategory newparent = new TbContentCategory();
            newparent.setId(parent.getId());
            newparent.setUpdated(date);
            newparent.setIsParent(true);
            int result = tbContentCategoryMapper.updateByPrimaryKeySelective(newparent);
            if (i==0){
                throw new EgoException("父節點狀態修改失敗");

            }
        }


        if (tcc.getId()!=null){
            return tcc.getId();
        }

        return 0L;
    }


    @Override
    @Transactional
    public int updateName(TbContentCategory tcc) {

        //先查詢是否有同名的，如果有直接失敗
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andStatusEqualTo(1).andNameEqualTo(tcc.getName());
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        if (list!=null&&list.size()>0){
            return 0;
        }
        Date date = new Date();
        tcc.setUpdated(date);





        return tbContentCategoryMapper.updateByPrimaryKeySelective(tcc);
    }


    @Override
    @Transactional
    public int delete(Long id) throws EgoException{
        TbContentCategory tbContentCategory = new TbContentCategory();
        Date date = new Date();
        tbContentCategory.setId(id);
        tbContentCategory.setUpdated(date);
        tbContentCategory.setStatus(2);

        int i = tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        if (i==1){
            //更改本項目成功

            TbContentCategory category = tbContentCategoryMapper.selectByPrimaryKey(id);
            //查詢父節點是否還有子節點
            TbContentCategoryExample example = new TbContentCategoryExample();
            example.createCriteria().andParentIdEqualTo(category.getParentId()).andStatusEqualTo(1);
            long result = tbContentCategoryMapper.countByExample(example);
            if (result==0){
                //沒有字節點，更改父節點信息
                TbContentCategory parent = new TbContentCategory();
                parent.setId(category.getParentId());
                parent.setIsParent(false);
                parent.setUpdated(date);

                result=tbContentCategoryMapper.updateByPrimaryKeySelective(parent);
                if (result==0){
                    throw new EgoException("刪除項目分類-刪除父節點失敗");
                }


            }

            //查詢是不是下面有子節點
            upstatusById(tbContentCategory);
            return 1;

        }else{
            //更改本項目失敗
            return 0;
        }

    }

    private void upstatusById(TbContentCategory category) {
        int i = tbContentCategoryMapper.updateByPrimaryKeySelective(category);
        if (i==1){
            TbContentCategoryExample example = new TbContentCategoryExample();
            example.createCriteria().andParentIdEqualTo(category.getId());
            List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
            for (TbContentCategory tbc: list) {
                TbContentCategory child = new TbContentCategory();
                child.setId(tbc.getId());
                child.setUpdated(category.getUpdated());
                child.setStatus(2);
                upstatusById(child);

            }

        }else{
            //修改子節點失敗
            throw new EgoException("刪除分類-修改字節點失敗");
        }
    }
}
