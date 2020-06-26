import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jack
 * @date 2020/6/21 17:00
 */
public class ESOperate {

    private RestHighLevelClient client;

    @BeforeEach
    public void init() {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.0.146", 9200, "http")));
    }


    @AfterEach
    public void close() throws IOException {
        client.close();
    }

    @Test
    public void addDocument1() throws IOException {
        IndexRequest request = new IndexRequest("javaapi");
        request.id("1");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);
        client.index(request, RequestOptions.DEFAULT);
    }

    @Test
    public void addDocument2() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest request = new IndexRequest("javaapi")
                .id("2").source(jsonMap);
        client.index(request, RequestOptions.DEFAULT);
    }

    @Test
    public void addDocument3() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("user", "kimchy");
            builder.timeField("postDate", new Date());
            builder.field("message", "trying out Elasticsearch");
        }
        builder.endObject();
        IndexRequest request = new IndexRequest("javaapi")
                .id("3").source(builder);
        client.index(request, RequestOptions.DEFAULT);
    }


    @Test
    public void addDocument4() throws IOException {
        IndexRequest request = new IndexRequest("javaapi")
                .id("4")
                .source("user", "kimchy",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch");
        client.index(request, RequestOptions.DEFAULT);
    }


    @Test
    public void addDocumentBulk() throws IOException {
        BulkRequest request = new BulkRequest();
        // addDocument1
        request.add(new IndexRequest("javaapi").id("5")
                .source(XContentType.JSON, "user", "user5"));

        // addDocument2
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "user6");
        request.add(new IndexRequest("javaapi").id("6")
                .source(jsonMap));


        // addDocument3
        request.add(new IndexRequest("javaapi").id("7")
                .source(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("user", "user7")
                        .endObject()
                ));


        // addDocument4
        request.add(new IndexRequest("javaapi").id("8")
                .source("user", "user8"));

        client.bulk(request, RequestOptions.DEFAULT);

    }


    @Test
    public void getById() throws IOException {
        GetRequest getRequest = new GetRequest("javaapi", "1");
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println("index:" + getResponse.getIndex());
        System.out.println("id:" + getResponse.getId());
        System.out.println("source" + getResponse.getSourceAsString());
    }

    @Test
    public void getData() throws IOException{
        SearchRequest request = new SearchRequest("javaapi");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        查询全部
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

//       范围查询
//       searchSourceBuilder.query(QueryBuilders.rangeQuery("age").gt("12").lt("20"));

//        精确查询
//        searchSourceBuilder.query(QueryBuilders.termQuery("user","user5"));
//          也可以这么写
//        searchSourceBuilder.query(new TermQueryBuilder("user","user5"));

//        纠正查询（英文）最大纠正次数两次
//        searchSourceBuilder.query(new FuzzyQueryBuilder("user","usar5"));

//        通配符查询 *多个，?一个
//        searchSourceBuilder.query(new WildcardQueryBuilder("user","user*"));



//        bool组合查询
//        RangeQueryBuilder age = QueryBuilders.rangeQuery("age").gt(17).lt(29);
//        TermQueryBuilder sex = QueryBuilders.termQuery("sex", "1");
//        RangeQueryBuilder id = QueryBuilders.rangeQuery("id").gt("9").lt("15");
//
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
//                .should(age)
//                .must(sex)
//                .mustNot(id);
//        searchSourceBuilder.query(boolQueryBuilder);



        request.source(searchSourceBuilder);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        for(SearchHit searchHit:search.getHits().getHits()) {
            System.out.println("================");
            System.out.println("index:" + searchHit.getIndex());
            System.out.println("id:" + searchHit.getId());
            System.out.println("source" + searchHit.getSourceAsString());
        }
    }

    /**
     * size+from浅分页
     * @throws IOException
     */
    @Test
    public void page() throws IOException {
        int  pageSize = 5;
        int pageNum = 2;
        int startNum = (pageNum-1)*5;
        SearchRequest searchRequest = new SearchRequest("javaapi");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(startNum);
        searchSourceBuilder.size(pageSize);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        for(SearchHit searchHit:searchResponse.getHits()) {
            System.out.println("================");
            System.out.println("index:" + searchHit.getIndex());
            System.out.println("id:" + searchHit.getId());
            System.out.println("source" + searchHit.getSourceAsString());
        }
    }

    /**
     *  Scroll 深分页
     * @throws IOException
     */
    @Test
    public void pageScroll() throws IOException {
        int size = 2;
        SearchRequest searchRequest = new SearchRequest("javaapi");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.size(size);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        String scrollId = searchResponse.getScrollId();
        for(SearchHit searchHit:searchResponse.getHits()) {
            System.out.println("================");
            System.out.println("index:" + searchHit.getIndex());
            System.out.println("id:" + searchHit.getId());
            System.out.println("source" + searchHit.getSourceAsString());
        }
        System.out.println("================");
        System.out.println(scrollId);
        System.out.println("================");


        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
        scrollRequest.scroll(TimeValue.timeValueSeconds(30));
        SearchResponse searchScrollResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
        scrollId = searchScrollResponse.getScrollId();
        for(SearchHit searchHit:searchScrollResponse.getHits()) {
            System.out.println("================");
            System.out.println("index:" + searchHit.getIndex());
            System.out.println("id:" + searchHit.getId());
            System.out.println("source" + searchHit.getSourceAsString());
        }
        System.out.println("================");
        System.out.println(scrollId);
        System.out.println("================");
    }

    /**
     * 高亮
     * @throws IOException
     */
    @Test
    public void highLight() throws IOException {

        SearchRequest searchRequest = new SearchRequest("javaapi");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("user","user6"));
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("user");
        highlightBuilder.preTags("<font>");
        highlightBuilder.postTags("</font>");
        searchSourceBuilder.highlighter(highlightBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchScrollResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        for(SearchHit searchHit:searchScrollResponse.getHits()) {
            System.out.println("================");
            System.out.println("index:" + searchHit.getIndex());
            System.out.println("id:" + searchHit.getId());
            System.out.println("source:" + searchHit.getSourceAsString());
            Text[] users = searchHit.getHighlightFields().get("user").getFragments();
            for(Text text : users){
                System.out.println(text);
            }
        }
    }

        @Test
        public void aggregation() throws IOException {

            SearchRequest searchRequest = new SearchRequest("javaapi");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("user_count")
//                .field("user");
            TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("user_count")
                    .field("message");
            searchSourceBuilder.aggregation(termsAggregationBuilder);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            Aggregations aggregations = searchResponse.getAggregations();
            for (Aggregation aggregation : aggregations) {
                System.out.println(aggregation.toString());
                StringTerms stringTerms = (StringTerms) aggregation;
                List<StringTerms.Bucket> buckets = stringTerms.getBuckets();
                for (StringTerms.Bucket bucket : buckets) {
                    System.out.println(bucket.getKey());
                    System.out.println(bucket.getDocCount());
                }
            }

    }



}
