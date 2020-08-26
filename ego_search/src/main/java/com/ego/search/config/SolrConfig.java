package com.ego.search.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;

/**
 * @Auther: liuxw
 * @Date: 2019/8/14
 * @Description: com.ego.search.config
 * @version: 1.0
 */
@Configuration
public class SolrConfig {
    @Autowired
    private SolrClient solrClient;

    @Value("${mysolr.collection}")
    private String collection;

    @Bean
    public SolrTemplate getTemplate(){
        CloudSolrClient csc = (CloudSolrClient) solrClient;
        csc.setDefaultCollection(collection);
        SolrTemplate solrTemplate = new SolrTemplate(csc);
        return solrTemplate;
    }
}
