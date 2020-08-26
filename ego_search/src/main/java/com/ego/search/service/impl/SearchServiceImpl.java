package com.ego.search.service.impl;

import com.ego.commons.pojo.SearchResultEntity;
import com.ego.dubbo.service.TBItemCatDubboService;
import com.ego.dubbo.service.TBItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.search.pojo.SolrEntity;
import com.ego.search.service.SearchService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: liuxw
 * @Date: 2019/8/14
 * @Description: com.ego.search.service.impl
 * @version: 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Reference
    private TbItemDubboService tbItemDubboService;
    @Reference
    private TBItemCatDubboService tbItemCatDubboService;
    @Reference
    private TBItemDescDubboService tbItemDescDubboService;

    @Autowired
    private SolrTemplate solrTemplate;
    @Value("${mysolr.collection}")
    private String collection;


    @Override
    public void init() {

        List<TbItem> list = tbItemDubboService.selectAll();
        for (TbItem item: list) {

            SolrEntity se = new SolrEntity();
            se.setId(item.getId()+"");
            se.setItem_title(item.getTitle());
            se.setItem_price(item.getPrice()+"");
            se.setItem_sell_point(item.getSellPoint());
            se.setItem_image(item.getImage());
            se.setItem_category_name(tbItemCatDubboService.selectByid(item.getCid()).getName());
            se.setItem_desc(tbItemDescDubboService.selectById(item.getId()).getItemDesc());
            solrTemplate.saveBean(collection,se);

            
        }
        solrTemplate.commit(collection);


    }


    @Override
    public Map<String, Object> search(String q, int page, int rows) {
        // 最终返回结果，jsp中有需要的内容
        Map<String,Object> map = new HashMap<>();
        //查询体，solr管理界面中的q部分 ，构造方法中的参数是查询时的属性名
        Criteria criteria = new Criteria("item_keywords");
        //q的条件值
        criteria.is(q);
        //高亮查询
        HighlightQuery query = new SimpleHighlightQuery(criteria);

        //高亮查询条件
        HighlightOptions options = new HighlightOptions();

        //高亮属性
        options.addField("item_title item_sell_point");

        //高亮前后缀设置
        options.setSimplePrefix("<span style='color:red'>");

        options.setSimplePostfix("</span>");
        //应用高亮条件
        query.setHighlightOptions(options);

        //分页查询-起始行
        query.setOffset((long)(rows*(page-1)));
        //总条数
        query.setRows(rows);
        //执行查询
        HighlightPage<SolrEntity> highlightPage = solrTemplate.queryForHighlightPage(collection,query,SolrEntity.class);
        //页面显示需要的数据
        List<SearchResultEntity> listResult = new ArrayList<SearchResultEntity>();

        //获取solr相应结果中highlighting部分
        List<HighlightEntry<SolrEntity>> listHL = highlightPage.getHighlighted();

        for (HighlightEntry<SolrEntity> hle:listHL){
            //获得每个实体--相当于
            SolrEntity hlEntity = hle.getEntity();
            //创建需要的实体--非高亮数据
            SearchResultEntity entity = new SearchResultEntity();
            //给最终实体进行赋值
            entity.setId(hlEntity.getId());
            entity.setPrice(Long.parseLong(hlEntity.getItem_price()));
            String image = hlEntity.getItem_image();
            //页面中需要的时图片数组
            entity.setImages(image!=null&&!image.equals("")?image.split(","):new String[0]);
            //高亮显示部分单独显示

            //实际高亮数据
            List<HighlightEntry.Highlight> listHEH = hle.getHighlights();
            //遍历给符合条件的高亮部分进行赋值
            for (HighlightEntry.Highlight heh: listHEH) {

                if (heh.getField().getName().equals("item_title")){
                    System.out.println("执行if"+heh.getSnipplets().get(0));
                    //获取高亮显示数据，放入到实体类
                    entity.setTitle(heh.getSnipplets().get(0));
                }else{
                    System.out.println("执行else"+hlEntity.getItem_title());
                    //获取非高亮数据
                    entity.setTitle(hlEntity.getItem_title());
                }


                if (heh.getField().getName().equals("item_sell_point")){
                    entity.setSellPoint(heh.getSnipplets().get(0));
                }else{
                    entity.setSellPoint(hlEntity.getItem_sell_point());
                }
            }
            listResult.add(entity);
        }
        //根据search.jsp所需要的数据
        map.put("itemList",listResult);
        map.put("totalPages",highlightPage.getTotalPages());
        map.put("page",page);
        map.put("query",q);
        return map;
    }


    @Override
    public int save(SolrEntity se) {
        UpdateResponse ur = solrTemplate.saveBean(collection, se);
        solrTemplate.commit(collection);
        if (ur.getStatus()==0){
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(List<String> ids) {
        UpdateResponse ur = solrTemplate.deleteByIds(collection, ids);
        solrTemplate.commit(collection);
        if (ur.getStatus()==0){
            return 1;
        }
        return 0;
    }
}
