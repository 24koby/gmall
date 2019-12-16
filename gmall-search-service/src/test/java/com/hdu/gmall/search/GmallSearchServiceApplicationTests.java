package com.hdu.gmall.search;


import com.alibaba.dubbo.config.annotation.Reference;
import com.hdu.gmall.bean.PmsSearchSkuInfo;
import com.hdu.gmall.bean.PmsSkuInfo;
import com.hdu.gmall.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallSearchServiceApplicationTests {

    @Reference
    SkuService skuService;

    @Autowired
    JestClient jestClient;

    @Test
    public void contextLoads() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        query
        searchSourceBuilder.query(null);
//        bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
//        filter
        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId","39");
        boolQueryBuilder.filter(termQueryBuilder);
//        must
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName","华为");
        boolQueryBuilder.must(matchQueryBuilder);
//        from
        searchSourceBuilder.from(0);
//        size
        searchSourceBuilder.size(20);
//        highlight
        HighlightBuilder highlighter = searchSourceBuilder.highlighter();

        String dslSearch = searchSourceBuilder.toString();
        System.out.println(dslSearch);
        get(dslSearch);
    }

    public void get(String dslToString) throws IOException {
        List<PmsSearchSkuInfo> pmsSearchSkuInfoList = new ArrayList();
//        Search search = new Search.Builder("{\n" +
//                "  \"query\": {\n" +
//                "    \"bool\": {\n" +
//                "      \"filter\": [\n" +
//                "          {\n" +
//                "            \"terms\":{\n" +
//                "              \"skuAttrValueList.valueId\":[\"39\",\"40\",\"41\"]\n" +
//                "            }\n" +
//                "          },\n" +
//                "          {\n" +
//                "            \"term\":{\n" +
//                "              \"skuAttrValueList.valueId\":\"39\"\n" +
//                "            }\n" +
//                "          },{\n" +
//                "            \"term\":{\n" +
//                "              \"skuAttrValueList.valueId\":\"39\"\n" +
//                "            }\n" +
//                "          }\n" +
//                "        ],\n" +
//                "        \"must\": [\n" +
//                "          {\n" +
//                "            \"match\": {\n" +
//                "              \"skuName\": \"华为\"\n" +
//                "            }\n" +
//                "          }\n" +
//                "        ]\n" +
//                "    }\n" +
//                "  }\n" +
//                "}").addIndex("gmall").addType("PmsSkuInfo").build();
        Search search = new Search.Builder(dslToString).addIndex("gmall").addType("PmsSkuInfo").build();
        SearchResult execute = jestClient.execute(search);
        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);
        for (SearchResult.Hit<PmsSearchSkuInfo,Void> hit: hits){
            PmsSearchSkuInfo source = hit.source;
            System.out.println(source.getSkuName());
            pmsSearchSkuInfoList.add(source);
        }
    }

    public void put() throws IOException {
        List<PmsSkuInfo> skuInfoList = new ArrayList();
        skuInfoList = skuService.getAllSku("61");
        List<PmsSearchSkuInfo> pmsSearchSkuInfoList = new ArrayList();

        for (PmsSkuInfo skuInfo:skuInfoList) {
            PmsSearchSkuInfo searchSkuInfo = new PmsSearchSkuInfo();
            BeanUtils.copyProperties(skuInfo,searchSkuInfo);
            pmsSearchSkuInfoList.add(searchSkuInfo);
        }
        for (PmsSearchSkuInfo searchSkuInfo:pmsSearchSkuInfoList) {
            Index put = new Index.Builder(searchSkuInfo).index("gmall").type("PmsSkuInfo").id(searchSkuInfo.getId()).build();
            jestClient.execute(put);
        }
    }


}
