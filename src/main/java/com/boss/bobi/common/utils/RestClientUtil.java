package com.boss.bobi.common.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * HTTP Rest Util
 * @author gyq
 *
 */
@Service
public class RestClientUtil {

    @Autowired
    RestTemplate restTemplate;
    /**
     *
     * @param <T>
     * @param url
     * @param clasz
     * @return
     */
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 100, multiplier = 2))
    public <T> T get(String url, Class<T> clasz) {
        return restTemplate.getForObject(url , clasz);
    }

    /**
     *
     * @param url
     * @param headMap
     * @param data
     * @param clasz
     * @param <T>
     * @return
     */
    public <T> T get(String url, Map<String, String> headMap, Map<String, Object> data, Class<T> clasz) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        if(null != headMap) {
            headMap.entrySet().forEach(item -> {
                headers.add(item.getKey(), item.getValue());
            });
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if (data != null) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }

        HttpEntity<String> formEntity = new HttpEntity<String>(null, headers);
        return restTemplate.exchange(builder.build().toString(), HttpMethod.GET, formEntity, clasz).getBody();
    }

    /**
     *
     * @param <T>
     * @param url
     * @param headMap
     * @param bodyObj
     * @param clasz
     * @return
     */
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 100, multiplier = 2))
    public <T> T postJson(String url, Map<String, String> headMap, Object bodyObj, Class<T> clasz) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        if(null != headMap) {
            headMap.entrySet().forEach(item -> {
                headers.add(item.getKey(), item.getValue());
            });
        }
        String result = null;
        if(bodyObj == null){
            result = "{}";
        }else{
            result = JSON.toJSONString(bodyObj);
        }
        HttpEntity<String> formEntity = new HttpEntity<String>(result,headers);
        return restTemplate.postForObject(url , formEntity, clasz);
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 100, multiplier = 2))
    public <T> T postUploadFileJson(String url, Map<String, String> headMap, Object bodyObj, Class<T> clasz) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/octet-stream");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        if(null != headMap) {
            headMap.entrySet().forEach(item -> {
                headers.add(item.getKey(), item.getValue());
            });
        }
        String result = null;
        if(bodyObj == null){
            result = "{}";
        }else{
            result = JSON.toJSONString(bodyObj);
        }
        HttpEntity<String> formEntity = new HttpEntity<String>(result,headers);
        return restTemplate.postForObject(url , formEntity, clasz);
    }


    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 100, multiplier = 2))
    public <T> T postYJson(String url, Map<String, String> headMap, Object bodyObj, Class<T> clasz) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        if(null != headMap) {
            headMap.entrySet().forEach(item -> {
                headers.add(item.getKey(), item.getValue());
            });
        }
        String result = null;
        if(bodyObj == null){
            result = "{}";
        }else{
            result = JSON.toJSONString(bodyObj);
        }
        HttpEntity<String> formEntity = new HttpEntity<String>(result,headers);
        return restTemplate.postForObject(url , formEntity, clasz);
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 100, multiplier = 2))
    public String putUpload(String url,  byte[] bodyObj) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpEntity<byte[]> formEntity = new HttpEntity<>(bodyObj,headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, formEntity, String.class);
        return response.getBody();

    }

    /**
     * 删除@Retryable注解
     * @param url
     * @param headMap
     * @param bodyObj
     * @param clasz
     * @return
     * @param <T>
     */
    public <T> T postJsonNoRetry(String url, Map<String, String> headMap, Object bodyObj, Class<T> clasz) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        if(null != headMap) {
            headMap.entrySet().forEach(item -> {
                headers.add(item.getKey(), item.getValue());
            });
        }
        String result = null;
        if(bodyObj == null){
            result = "{}";
        }else{
            result = JSON.toJSONString(bodyObj);
        }
        HttpEntity<String> formEntity = new HttpEntity<String>(result,headers);
        return restTemplate.postForObject(url , formEntity, clasz);
    }

    /**
     *
     * @param <T>
     * @param url
     * @param attrMap
     * @param clasz
     * @return
     */
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 100, multiplier = 2))
    public <T> T postForm(String url, Map<String , String> attrMap, Class<T> clasz){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        attrMap.entrySet().forEach(item -> {
            params.add(item.getKey() , item.getValue());

        });
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, clasz).getBody();
    }

}