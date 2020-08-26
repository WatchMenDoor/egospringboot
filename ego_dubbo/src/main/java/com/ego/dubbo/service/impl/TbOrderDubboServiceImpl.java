package com.ego.dubbo.service.impl;

import com.ego.commons.exception.ItemNumNotEnoughException;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbOrderItemMapper;
import com.ego.mapper.TbOrderMapper;
import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/20
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbOrderDubboServiceImpl implements TbOrderDubboService {
    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;

    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;
    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    @Transactional
    public String save(TbOrder tbOrder, List<TbOrderItem> list, TbOrderShipping tbOrderShipping) throws ItemNumNotEnoughException {
        //创建id
        String orderId = IDUtils.genItemId()+"";
        tbOrder.setOrderId(orderId);
        Date date = new Date();
        tbOrder.setCreateTime(date);
        tbOrder.setUpdateTime(date);
        //order表中的信息
        int i = tbOrderMapper.insertSelective(tbOrder);

         //订单提交后，更改商品在库信息
        for (TbOrderItem tbOrderItem : list){
            tbOrderItem.setOrderId(orderId);
            tbOrderItem.setId(IDUtils.genItemId()+"");
            //判断是否库存数量足够
            long itemId = Long.parseLong(tbOrderItem.getItemId());
            TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
            if (tbItem.getNum()>=tbOrderItem.getNum()){
                TbItem updateitem = new TbItem();
                updateitem.setId(itemId);
                updateitem.setNum(tbItem.getNum()-tbOrderItem.getNum());
                updateitem.setUpdated(date);
                tbItemMapper.updateByPrimaryKeySelective(updateitem);
            }else{
                //库存不足
               throw new ItemNumNotEnoughException("商品数量不足");
            }
            //orderItem表中的信息
            i+=tbOrderItemMapper.insertSelective(tbOrderItem);
        }

        tbOrderShipping.setCreated(date);
        tbOrderShipping.setUpdated(date);
        tbOrderShipping.setOrderId(orderId);
        i+=tbOrderShippingMapper.insertSelective(tbOrderShipping);

        if (i==2+list.size()){


            return orderId;
        }

        return null;
    }
}
