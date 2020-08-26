package com.ego.search.test;

import com.ego.SearchApplication;
import com.ego.search.pojo.SolrEntity;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Auther: liuxw
 * @Date: 2019/8/16
 * @Description: com.ego.search.test
 * @version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class SearchTest {
    @Autowired
    private SolrTemplate solrTemplate;

    @Value("${mysolr.collection}")
    private String collectionName;

    public void testSave(){
        SolrInputDocument doc = new SolrInputDocument();

        doc.addField("id", 1);
        doc.addField("item_title", "标题");
        doc.addField("item_sell_point", "卖点");
        doc.addField("item_price", 998);
        doc.addField("item_image", "http://www.baidu.com");
        doc.addField("item_category_name","手机");
        doc.addField("item_desc", "描述");

        UpdateResponse ur = solrTemplate.saveDocument(collectionName, doc);
        solrTemplate.commit(collectionName);
        if (ur.getStatus()==0){
            System.out.println("成功");
        }else{
            System.out.println("失败");
        }
    }

    @Test
    public void testSaveBean(){
        SolrEntity se = new SolrEntity();


        se.setId("3");
        se.setItem_category_name("手机");
        se.setItem_desc("描述");
        se.setItem_image("http");
        se.setItem_price("98");
        se.setItem_title("标题");
        se.setItem_sell_point("卖点");
    }
}
