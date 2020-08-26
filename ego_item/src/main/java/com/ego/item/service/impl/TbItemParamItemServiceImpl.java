package com.ego.item.service.impl;

import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.item.pojo.ParamData;
import com.ego.item.pojo.SmallParams;
import com.ego.item.service.TbItemParamItemService;
import com.ego.pojo.TbItemParamItem;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/15
 * @Description: com.ego.item.service.impl
 * @version: 1.0
 */
@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {


    @Reference
    private TbItemParamItemDubboService tbItemParamItemDubboService;

    @Override
    public String selectParamById(Long id) {
        TbItemParamItem tbItemParamItem = tbItemParamItemDubboService.seletByItemID(id);
        if (tbItemParamItem!=null){
            String paramData = tbItemParamItem.getParamData();
            List<ParamData> list = JsonUtils.jsonToList(paramData, ParamData.class);

            StringBuffer sf = new StringBuffer();
            for (ParamData ipp:list) {
                sf.append("<table border='1' style='color:gray';width='100%'>");
                for (int i=0;i<ipp.getParams().size();i++) {
                    sf.append("<tr>");
                    if (i==0){
                        sf.append("<td>"+ipp.getGroup()+"</td>");
                    }else{
                        sf.append("<td></td>");
                    }
                    sf.append("<td>"+ipp.getParams().get(i).getK()+"</td>");
                    sf.append("<td>"+ipp.getParams().get(i).getV()+"</td>");
                    sf.append("</tr>");
                }

                sf.append("</table>");
                sf.append("<hr style='color:gray;'/>");

            }
            return sf.toString();
        }
        return "此商品没有规格参数";
    }
}
