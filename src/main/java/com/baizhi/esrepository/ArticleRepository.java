package com.baizhi.esrepository;

import com.baizhi.dao.ArticleDAO;
import com.baizhi.entity.Article;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleRepository {
    @Autowired
    ArticleDAO articleDAO;
    @Autowired
    TransportClient transportClient;

    //es  添加数据到es中
    public void add(Article article) throws IOException {
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder().startObject()
                .field("title", article.getTitle())
                .field("state", article.getState())
                .field("author", article.getAuthor())
                .field("content", article.getContent())
                .field("date", article.getDate())
                .endObject();
        transportClient.prepareIndex("cmfz", "article", article.getId()).setSource(xContentBuilder).get();
    }

    //es多字段查询
    public List<Map> search(String query, Integer start, Integer rows) {
        ArrayList<Map> list = new ArrayList<>();
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(query)
                .analyzer("ik_max_word") //定义分词器
                .field("title")
                .field("author")
                .field("content");//定义查询的字段
        SearchResponse searchResponse = transportClient.prepareSearch("cmfz")
                .setTypes("article")
                .setQuery(queryStringQueryBuilder)
                .setFrom(start)
                .setSize(rows)
                .get();
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            String id = hit.getId();
            Map<String, Object> map = hit.getSourceAsMap();
            map.put("id", id);
            list.add(map);
        }
        return list;
    }

    //es返回条数
    public Integer searchCount(String query, Integer start, Integer rows) {
        ArrayList<Map> list = new ArrayList<>();
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(query)
                .analyzer("ik_max_word") //定义分词器
                .field("title")
                .field("author")
                .field("content");//定义查询的字段
        SearchResponse searchResponse = transportClient.prepareSearch("cmfz")
                .setTypes("article")
                .setQuery(queryStringQueryBuilder)
                .setFrom(start)
                .setSize(rows)
                .get();
        Integer count = Math.toIntExact(searchResponse.getHits().getTotalHits());
        return count;
    }
}
