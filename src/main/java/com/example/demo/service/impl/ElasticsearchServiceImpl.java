package com.example.demo.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.ElasticsearchService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.stream.Stream;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;

import org.elasticsearch.action.fieldcaps.FieldCapabilitiesRequest;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.GetSourceRequest;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    private static RestHighLevelClient restHighLevelClient;

    public ElasticsearchServiceImpl(RestHighLevelClient restHighLevelClient) {
        ElasticsearchServiceImpl.restHighLevelClient = restHighLevelClient;
    }

    /**
     * ?????? resource????????????????????????
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
     * ??????????????????
     */
    public String getNow(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String str=df.format(new Date());
        return str;
    }
    /**
     * ????????????
     */
    @Override
    public void createIndex(String idxName){
        try {
            if (indexExist(idxName)) {
                System.out.println("???????????????");
                return;
            }
            CreateIndexRequest request = new CreateIndexRequest(idxName);

            CreateIndexResponse res = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            if (!res.isAcknowledged()) {
                throw new RuntimeException("???????????????");
            }
            System.out.println("????????????");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("createIndex is FAIL!!");
            throw new RuntimeException(e);
        }
    }

    /**
     * @param idxName index???
     * @return boolean
     * @Description: <h2>?????????index????????????</h2>
     * @author: LiRen
     */
    public static boolean indexExist(String idxName) throws Exception {
        return restHighLevelClient.indices().exists(new GetIndexRequest(idxName),RequestOptions.DEFAULT);
    }


    public void index() throws IOException {

        //??????????????????????????????;
        IndexRequest indexRequest=new IndexRequest("129","docs");

        indexRequest.id("1");

        //???????????????
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        indexRequest.source(jsonString, XContentType.JSON);

        indexRequest.source(XContentType.JSON,jsonString);

        //???????????????
        List<T> list=new ArrayList<>();
        Map map= Optional.of(list).stream().collect(Collectors.toMap(T->T.toString(),T->T.hashCode()));
        indexRequest.source(map);

        //???????????????
        XContentBuilder builder= XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("","");
            builder.timeField("","");
        }
        builder.endObject();
        indexRequest.source(builder);

        IndexResponse response=restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);
    }

    /**
     *
     * get api
     */
    public void get() throws IOException {

        GetRequest getRequest=new GetRequest("","");
        String[] includes=new String[]{"","",""};
        String[] excludes=new String[]{"","",""};
        FetchSourceContext fetchSourceContext=new FetchSourceContext(true,includes,excludes);
        getRequest.fetchSourceContext(fetchSourceContext);
        GetResponse response=restHighLevelClient.get(getRequest,RequestOptions.DEFAULT);

        GetSourceRequest request=new GetSourceRequest("","");

        //?????????

    }

    /**
     *
     * exists api
     */
     public void exists() throws IOException{
         GetRequest getRequest=new GetRequest("","");
         restHighLevelClient.exists(getRequest,RequestOptions.DEFAULT);

         GetSourceRequest request=new GetSourceRequest("","");
         restHighLevelClient.existsSource(request,RequestOptions.DEFAULT);

     }

    /**
     *
     * update api
     */

    public void update() throws IOException {
        UpdateRequest request=new UpdateRequest("","");
        XContentBuilder builder=XContentFactory.jsonBuilder();

        request.doc(builder);
        restHighLevelClient.update(request,RequestOptions.DEFAULT);
    }

    /**
     *
     *Bulk api
     */
    public void bulk() throws IOException {
        BulkRequest bulkRequest=new BulkRequest();
        bulkRequest.add(new IndexRequest("","").source(new JSONObject(),XContentType.JSON))
                .add(new UpdateRequest("","").doc(new JSONObject(),XContentType.JSON))
                .add(new DeleteRequest("",""));
        restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);

    }


    /**
     *
     *
     * Reindex api
     */
    public void reindex() throws IOException {
        ReindexRequest request=new ReindexRequest();
        request.setSourceIndices("","");
        request.setDestIndex("");
        BulkByScrollResponse response=restHighLevelClient.reindex(request,RequestOptions.DEFAULT);
    }

    /**
     *
     * updateBYquery
     */
    public void updateByQuery() throws IOException {
        UpdateByQueryRequest update=new UpdateByQueryRequest("","");
        update.indices("","");
        update.setQuery(new TermQueryBuilder("",""));
        restHighLevelClient.updateByQueryAsync(update, RequestOptions.DEFAULT, new ActionListener<BulkByScrollResponse>() {
            @Override
            public void onResponse(BulkByScrollResponse b) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    /**
     *
     * ?????? search api
     */
    public void search()  {
        SearchRequest request=new SearchRequest("");
        SearchSourceBuilder builder=new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
        boolQueryBuilder.must(new TermQueryBuilder("","")).should(new TermQueryBuilder("",""));

        builder.query(boolQueryBuilder);
        request.source(builder);
        try{
            restHighLevelClient.search(request,RequestOptions.DEFAULT);
        }catch (IOException e){
            log.error("",e);
        }

    }
    /**
     *
     * FieldCapabilitiesRequest
     */
    public void FieldCapabilities() throws IOException {
        FieldCapabilitiesRequest request=new FieldCapabilitiesRequest();
        request.fields("").indices("","","");
        FieldCapabilitiesResponse response=restHighLevelClient.fieldCaps(request,RequestOptions.DEFAULT);
    }



    /**
     * ????????????
     */
    @Override
    public  void createDoc(String idxName, String path){
        Map<String ,Object> jsonMap=readJsonFile(path);
        jsonMap.put("create_time",getNow());
        if(!jsonMap.isEmpty()){
        IndexRequest request=new IndexRequest(idxName,"doc").source(jsonMap);
        restHighLevelClient.indexAsync(request, RequestOptions.DEFAULT,new ActionListener<IndexResponse>(){
            @Override
            public void onResponse(IndexResponse indexResponse) {
                if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                    // ?????????????????????
                    System.out.println("?????????????????????,????????????");
                } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                    // ???????????????????????????????????????
                    System.out.println("???????????????????????????????????????");
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        }else {
            System.out.println("???????????????????????????");
        }
    }



    /**
     *
     *
     * ????????????
     */
    @Override
    public void searchall(String idxName){
        SearchRequest searchRequest = new SearchRequest(idxName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery()); // ?????? match_all ??????
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder); // ??? SearchSourceBuilder  ????????? SeachRequest ???
        restHighLevelClient.searchAsync(searchRequest,RequestOptions.DEFAULT,new ActionListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                    System.out.println("????????????");
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
                System.out.println("????????????");
            }
        });
    }

    /**
    *
    * ??????
    */
    @Override
    public void deleteDoc(String index, String type, String id){
        DeleteRequest request=new DeleteRequest(index,type,id);
        restHighLevelClient.deleteAsync(request, RequestOptions.DEFAULT, new ActionListener<DeleteResponse>() {
            @Override
            public void onResponse(DeleteResponse deleteResponse) {
                System.out.println("????????????");
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("????????????");
            }
        });
    }



    /**
     *
     * ??????????????????
     */
     @Override
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
                             // ?????????????????????
                             System.out.println("?????????????????????,????????????");
                         } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                             // ???????????????????????????????????????
                             System.out.println("???????????????????????????????????????");
                         }
                     }

                     @Override
                     public void onFailure(Exception e) {

                     }
                 });
             }else {
                 System.out.println("???????????????????????????");
             }
         }
     }

    /**
     *
     * ???????????????
     */
    @Override
    public void searchDocs(String idxName, List<String> list){
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
        BoolQueryBuilder childBoolQueryBuilder = new BoolQueryBuilder();
        for(String str:list){
            // ??????boolQueryBuilder??????
            MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders
                    .matchPhraseQuery("name",str);
            childBoolQueryBuilder.should(matchPhraseQueryBuilder);
        }

        // ???????????????SearchSourceBuilder??????
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        boolQueryBuilder.must(childBoolQueryBuilder);
        // ????????????--->??????DSL????????????
        searchSourceBuilder.query(boolQueryBuilder);
        SearchRequest searchRequest=new SearchRequest().indices(idxName).source(searchSourceBuilder);
        restHighLevelClient.searchAsync(searchRequest,RequestOptions.DEFAULT,new ActionListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                System.out.println("????????????");
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
                System.out.println("????????????");
            }
        });

    }
    /**
     *
     * ???????????????
     */
    @Override
    public void searchDocss(String idxName, String str){
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
        BoolQueryBuilder childBoolQueryBuilder = new BoolQueryBuilder();
        String[] arr4 = str.split("\\s+");
        for(String s:arr4){
            // ??????boolQueryBuilder??????
            MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders
                    .matchPhraseQuery("name",s);
            childBoolQueryBuilder.should(matchPhraseQueryBuilder);
        }

        // ???????????????SearchSourceBuilder??????
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        boolQueryBuilder.must(childBoolQueryBuilder);
        // ????????????--->??????DSL????????????
        searchSourceBuilder.query(boolQueryBuilder);
        SearchRequest searchRequest=new SearchRequest().indices(idxName).source(searchSourceBuilder);
        restHighLevelClient.searchAsync(searchRequest,RequestOptions.DEFAULT,new ActionListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                System.out.println("????????????");
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
                System.out.println("????????????");
            }
        });

    }
    /**
     *
     * ???????????????+?????????
     */
    @Override
    public void searchDocsss(String idxName, String str, String dateFormat){
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
        BoolQueryBuilder childBoolQueryBuilder = new BoolQueryBuilder();
        String[] arr4 = str.split("\\s+");
        for(String s:arr4){
            // ??????boolQueryBuilder??????
            MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders
                    .matchPhraseQuery("name",s);
            childBoolQueryBuilder.should(matchPhraseQueryBuilder);
        }
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders
                .rangeQuery("create_time.keyword")
                .from(UtcDate(dateFormat)).to(getNow());

        // ???????????????SearchSourceBuilder??????
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        boolQueryBuilder.must(childBoolQueryBuilder)
                        .must(rangeQueryBuilder);
        // ????????????--->??????DSL????????????
        searchSourceBuilder.query(boolQueryBuilder);
        SearchRequest searchRequest=new SearchRequest().indices(idxName).source(searchSourceBuilder);
        restHighLevelClient.searchAsync(searchRequest,RequestOptions.DEFAULT,new ActionListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                System.out.println("????????????");
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
                System.out.println("????????????");
            }
        });

    }



    /**
     *
     *
     * ?????????????????????
     */
    @Override
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
                System.out.println("????????????");
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
     * ???????????????????????????utc
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
     * ????????????
     */
    @Override
    public  void searchTermbyAge(){
        SearchRequest searchRequest=new SearchRequest();
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        AggregationBuilder aggregation = AggregationBuilders.terms("average_age")
                .field("age");
//        aggregation.subAggregation(AggregationBuilders.avg("age").field("age"));
        searchSourceBuilder.aggregation(aggregation);
         //????????????????????????
        searchRequest.indices("125").source(searchSourceBuilder);
        restHighLevelClient.searchAsync(searchRequest, RequestOptions.DEFAULT, new ActionListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                System.out.println("????????????");
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
                System.out.println("????????????");
                e.printStackTrace();
            }
        });
    }
}
