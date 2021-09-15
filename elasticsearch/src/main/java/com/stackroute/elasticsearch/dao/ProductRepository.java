package com.stackroute.elasticsearch.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.elasticsearch.model.Product;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class ProductRepository {
    private final String INDEX = "productdata";
    private final String TYPE = "product";
    private RestHighLevelClient restHighLevelClient;
    private ObjectMapper objectMapper;

    public ProductRepository(ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
        this.objectMapper = objectMapper;
        this.restHighLevelClient = restHighLevelClient;
    }
    public Map<String, Object> getProductById(UUID id){
        String s_id = id.toString();
        GetRequest getRequest = new GetRequest(INDEX, TYPE, s_id);
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getRequest);
        } catch (java.io.IOException e){
            e.getLocalizedMessage();
        }
        Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
        return sourceAsMap;
    }

    public Map<String, Object> updateProductById(UUID id, Product product){
        String s_id = id.toString();
        UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, s_id)
                .fetchSource(true);    // Fetch Object after its update
        Map<String, Object> error = new HashMap<>();
        error.put("Error", "Unable to update product");
        try {
            String bookJson = objectMapper.writeValueAsString(product);
            updateRequest.doc(bookJson, XContentType.JSON);
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest);
            Map<String, Object> sourceAsMap = updateResponse.getGetResult().sourceAsMap();
            return sourceAsMap;
        }catch (JsonProcessingException e){
            e.getMessage();
        } catch (java.io.IOException e){
            e.getLocalizedMessage();
        }
        return error;
    }
    public void deleteProductById(UUID id) {
        String s_id = id.toString();
        DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, s_id);
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest);
        } catch (java.io.IOException e){
            e.getLocalizedMessage();
        }
    }

    public Product insertProduct(Product product) {
       product.set_id(UUID.randomUUID());
        Map dataMap = objectMapper.convertValue(product, Map.class);
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, product.get_id().toString()).source(dataMap);
        try {
            IndexResponse response = restHighLevelClient.index(indexRequest);
        } catch(ElasticsearchException e) {
            e.getDetailedMessage();
        } catch (java.io.IOException ex){
            ex.getLocalizedMessage();
        }
        return product;
    }
}
