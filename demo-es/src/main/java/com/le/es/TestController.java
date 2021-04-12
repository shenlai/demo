package com.le.es;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.Contended;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shenlai
 * @Description TODO
 * @create 2020/9/27 11:39
 */

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource(name = "resourceRestHighLevelClient")
    private RestHighLevelClient restHighLevelClient;



    @RequestMapping("/esTest")
    public String test(){
        //log.info("-----------------es 二级分类 产品查询 ----------------");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.termQuery("goods.xcmchid", 6001));

        boolQueryBuilder.must(QueryBuilders.termQuery("goods.lg_wh_id", 16));
        boolQueryBuilder.must(QueryBuilders.termQuery("goods.is_valid", 1));
        boolQueryBuilder.must(QueryBuilders.termQuery("goods.is_gift_goods", 0));


        boolQueryBuilder.must(QueryBuilders.termQuery("goods.is_on_sale", 1));
        sourceBuilder.query(boolQueryBuilder);
        //默认会自动只取十条，这边设置无穷大
        sourceBuilder.from(0).size(2);
        sourceBuilder.sort("goods.shop_cat_id_2", SortOrder.DESC);
        sourceBuilder.sort("goods.sort", SortOrder.DESC);
        sourceBuilder.fetchSource(
                new String[]{},new String[]{"categortyList.*"});

        String indexName = "resource-goods_read";

        SearchRequest request = new SearchRequest(indexName);
//        request.types(indexType);

        request.source(sourceBuilder);
        SearchResponse response=null;


        try {
            //log.info("123");
            response = restHighLevelClient.search(request, RequestOptions.DEFAULT);


        }catch (Exception e){
            e.printStackTrace();
            //log.error(e.getMessage());
        }
        return "success";
    }


}
