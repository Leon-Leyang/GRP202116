package com.grp202116.backend.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpUtils {
    private static final HttpHeaders headers;

    static {
        headers  = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    }


    public static HttpEntity<String> parseJsonToFlask(String param) {
        return new HttpEntity<>(param, headers);
    }
}
