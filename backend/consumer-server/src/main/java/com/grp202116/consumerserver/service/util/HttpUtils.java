package com.grp202116.consumerserver.service.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * This HttpUtils class is used to generate a common {@link HttpHeaders}
 * to post or get requests to the flask server.
 *
 * @author Yujie Chen
 */
public class HttpUtils {
    private static final HttpHeaders headers;

    static {
        headers  = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    }


    /**
     * Specify the type to json
     *
     * @param param params to be transferred
     * @return a {@link HttpEntity} object
     */
    public static HttpEntity<String> parseJsonToFlask(String param) {
        return new HttpEntity<>(param, headers);
    }
}
