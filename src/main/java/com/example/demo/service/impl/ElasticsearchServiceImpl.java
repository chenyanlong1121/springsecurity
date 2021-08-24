package com.example.demo.service.impl;


import com.example.demo.service.ElasticsearchService;
import com.google.gson.Gson;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;



@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    private static RestHighLevelClient restHighLevelClient;

    public ElasticsearchServiceImpl(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    /**
     * 读取 resource目录下的文件内容
     * @param path
     * @return
     * @throws IOException
     */
    public Map<String, Object> readJsonFile(String path) {
        Gson gson = new Gson();
        String json = "";
        try {
            Resource resource = new ClassPathResource(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(),"UTF-8"));
            int ch = 0;
            StringBuffer buffer = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                buffer.append((char) ch);
            }
            reader.close();
            json = buffer.toString();
            return gson.fromJson(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取当前时间
     */
    public String getNow(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String str=df.format(new Date());
        return str;
    }
    /**
     * 创建索引
     */
    public void createIndex(String idxName){
        try {
            if (indexExist(idxName)) {
                System.out.println("索引已存在");
                return;
            }
            CreateIndexRequest request = new CreateIndexRequest(idxName);

            CreateIndexResponse res = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            if (!res.isAcknowledged()) {
                throw new RuntimeException("初始化失败");
            }
            System.out.println("创建成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("createIndex is FAIL!!");
            throw new RuntimeException(e);
        }
    }

    /**
     * @param idxName index名
     * @return boolean
     * @Description: <h2>断某个index是否存在</h2>
     * @author: LiRen
     */
    public static boolean indexExist(String idxName) throws Exception {
        return restHighLevelClient.indices().exists(new GetIndexRequest(idxName),RequestOptions.DEFAULT);
    }





    /**
     * 创建文档
     */
    public  void createDoc(String idxName, String path){
        Map<String ,Object> jsonMap=readJsonFile(path);
        jsonMap.put("create_time",getNow());
        if(!jsonMap.isEmpty()){
        IndexRequest request=new IndexRequest(idxName,"doc").source(jsonMap);
        restHighLevelClient.indexAsync(request, RequestOptions.DEFAULT,new ActionListener<IndexResponse>(){
            @Override
            public void onResponse(IndexResponse indexResponse) {
                if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                    // 文档第一次创建
                    System.out.println("文档第一次创建,创建成功");
                } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                    // 文档之前已存在，当前是重写
                    System.out.println("文档之前已存在，当前是重写");
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        }else {
            System.out.println("文件为空或者不存在");
        }
    }



    /**
     *
     *
     * 查询所有
     */
    public void searchall(String idxName){
        SearchRequest searchRequest = new SearchRequest(idxName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery()); // 添加 match_all 查询
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder); // 将 SearchSourceBuilder  添加到 SeachRequest 中
        restHighLevelClient.searchAsync(searchRequest,RequestOptions.DEFAULT,new ActionListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                    System.out.println("查询成功");
                    SearchHits hits =  searchResponse.getHits();
                    SearchHit[] searchHits = hits.getHits();
                    for (SearchHit hit : searchHits) {
                        // do something with the SearchHit
                        String sourceAsString = hit.getSourceAsString();
                        System.out.println(sourceAsString);
                    }
                }

            @Override
            public void onFailure(Exception e) {
                System.out.println("查询失败");
            }
        });
    }

    /**
    *
    * 删除
    */
    public void deleteDoc(String index,String type,String id){
        DeleteRequest request=new DeleteRequest(index,type,id);
        restHighLevelClient.deleteAsync(request, RequestOptions.DEFAULT, new ActionListener<DeleteResponse>() {
            @Override
            public void onResponse(DeleteResponse deleteResponse) {
                System.out.println("删除成功");
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("删除失败");
            }
        });
    }



    /**
     *
     * 批量插入文档
     */
     public void createDocs(String idxName, List<String> paths){
         for(String path:paths){
             Map<String,Object> jsonMap=readJsonFile(path);
             jsonMap.put("create_time",String.valueOf(getNow()));
             if(!jsonMap.isEmpty()){
                 IndexRequest request=new IndexRequest(idxName,"doc").source(jsonMap);
                 restHighLevelClient.indexAsync(request, RequestOptions.DEFAULT,new ActionListener<IndexResponse>(){
                     @Override
                     public void onResponse(IndexResponse indexResponse) {
                         if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                             // 文档第一次创建
                             System.out.println("文档第一次创建,创建成功");
                         } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                             // 文档之前已存在，当前是重写
                             System.out.println("文档之前已存在，当前是重写");
                         }
                     }

                     @Override
                     public void onFailure(Exception e) {

                     }
                 });
             }else {
                 System.out.println("文件为空或者不存在");
             }
         }
     }

    /**
     *
     * 多字段查询
     */
    public void searchDocs(String idxName,List<String> list){
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
        BoolQueryBuilder childBoolQueryBuilder = new BoolQueryBuilder();
        for(String str:list){
            // 设置boolQueryBuilder条件
            MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders
                    .matchPhraseQuery("name",str);
            childBoolQueryBuilder.should(matchPhraseQueryBuilder);
        }

        // 创建并设置SearchSourceBuilder对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        boolQueryBuilder.must(childBoolQueryBuilder);
        // 查询条件--->生成DSL查询语句
        searchSourceBuilder.query(boolQueryBuilder);
        SearchRequest searchRequest=new SearchRequest().indices(idxName).source(searchSourceBuilder);
        restHighLevelClient.searchAsync(searchRequest,RequestOptions.DEFAULT,new ActionListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                System.out.println("查询成功");
                SearchHits hits =  searchResponse.getHits();
                SearchHit[] searchHits = hits.getHits();
                for (SearchHit hit : searchHits) {
                    // do something with the SearchHit
                    String sourceAsString = hit.getSourceAsString();
                    System.out.println(sourceAsString);
                }
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("查询失败");
            }
        });

    }
    /**
     *
     * 多字段查询
     */
    public void searchDocss(String idxName,String str){
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
        BoolQueryBuilder childBoolQueryBuilder = new BoolQueryBuilder();
        String[] arr4 = str.split("\\s+");
        for(String s:arr4){
            // 设置boolQueryBuilder条件
            MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders
                    .matchPhraseQuery("name",s);
            childBoolQueryBuilder.should(matchPhraseQueryBuilder);
        }

        // 创建并设置SearchSourceBuilder对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        boolQueryBuilder.must(childBoolQueryBuilder);
        // 查询条件--->生成DSL查询语句
        searchSourceBuilder.query(boolQueryBuilder);
        SearchRequest searchRequest=new SearchRequest().indices(idxName).source(searchSourceBuilder);
        restHighLevelClient.searchAsync(searchRequest,RequestOptions.DEFAULT,new ActionListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                System.out.println("查询成功");
                SearchHits hits =  searchResponse.getHits();
                SearchHit[] searchHits = hits.getHits();
                for (SearchHit hit : searchHits) {
                    // do something with the SearchHit
                    String sourceAsString = hit.getSourceAsString();
                    System.out.println(sourceAsString);
                }
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("查询失败");
            }
        });

    }
    /**
     *
     * 多字段查询+时间段
     */
    public void searchDocsss(String idxName,String str,String dateFormat){
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
        BoolQueryBuilder childBoolQueryBuilder = new BoolQueryBuilder();
        String[] arr4 = str.split("\\s+");
        for(String s:arr4){
            // 设置boolQueryBuilder条件
            MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders
                    .matchPhraseQuery("name",s);
            childBoolQueryBuilder.should(matchPhraseQueryBuilder);
        }
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders
                .rangeQuery("create_time.keyword")
                .from(UtcDate(dateFormat)).to(getNow());

        // 创建并设置SearchSourceBuilder对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        boolQueryBuilder.must(childBoolQueryBuilder)
                        .must(rangeQueryBuilder);
        // 查询条件--->生成DSL查询语句
        searchSourceBuilder.query(boolQueryBuilder);
        SearchRequest searchRequest=new SearchRequest().indices(idxName).source(searchSourceBuilder);
        restHighLevelClient.searchAsync(searchRequest,RequestOptions.DEFAULT,new ActionListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                System.out.println("查询成功");
                SearchHits hits =  searchResponse.getHits();
                SearchHit[] searchHits = hits.getHits();
                for (SearchHit hit : searchHits) {
                    // do something with the SearchHit
                    String sourceAsString = hit.getSourceAsString();
                    System.out.println(sourceAsString);
                }
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("查询失败");
            }
        });

    }



    /**
     *
     *
     * 多字段模糊查询
     */
    public void searchFuzziness(String str){
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
        String[] arr=str.split("\\s+");
        for (String st:arr){
            WildcardQueryBuilder wildcardQueryBuilder=new WildcardQueryBuilder("name","*"+st+"*");
            boolQueryBuilder.should(wildcardQueryBuilder);
        }
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        SearchRequest searchRequest=new SearchRequest();
        searchRequest.indices("125").source(searchSourceBuilder);
        restHighLevelClient.searchAsync(searchRequest, RequestOptions.DEFAULT, new ActionListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                System.out.println("查询成功");
                SearchHits hits =  searchResponse.getHits();
                SearchHit[] searchHits = hits.getHits();
                for (SearchHit hit : searchHits) {
                    // do something with the SearchHit
                    String sourceAsString = hit.getSourceAsString();
                    System.out.println(sourceAsString);
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }


    /**
     *
     * 更改时间格式，要求utc
     */
    public String UtcDate(String date){
        String date2="";
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date1=format.parse(date);
            date2=format.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2;
    }
    /**
     *
     * 聚合查询
     */
    public  void searchTermbyAge(){
        SearchRequest searchRequest=new SearchRequest();
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        AggregationBuilder aggregation = AggregationBuilders.terms("average_age")
                .field("age.keyword");
        searchSourceBuilder.aggregation(aggregation);
         //对请求和聚合分析
        searchRequest.indices("125").source(searchSourceBuilder);
        restHighLevelClient.searchAsync(searchRequest, RequestOptions.DEFAULT, new ActionListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                System.out.println("查询成功");
                SearchHits hits =  searchResponse.getHits();
                SearchHit[] searchHits = hits.getHits();
                for (SearchHit hit : searchHits) {
                    // do something with the SearchHit
                    String sourceAsString = hit.getSourceAsString();
                    System.out.println(sourceAsString);
                }
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("查询失败");
                e.printStackTrace();
            }
        });
    }
}
