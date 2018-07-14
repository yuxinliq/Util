package com.baidu.translate.demo;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransApi {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private static final String appid = "20180714000185441";
    private static final String SECURITY_KEY = "Zb64XgmRwLjOpoqYj8JV";

    private static Map<String, TransResult> cache = new ConcurrentHashMap<>();

    public static TransResult getTransResult(String query) {
        return getTransResult(query, "auto", "zh");
    }

    public static TransResult getTransResult(String query, String from, String to) {
        String key = query + from + to;
        TransResult transResult = cache.get(key);
        if (transResult != null) {
            return transResult;
        }
        Map<String, String> params = buildParams(query, from, to);
        transResult = JSON.parseObject(HttpGet.get(TRANS_API_HOST, params), TransResult.class);
        cache.put(key, transResult);
        return transResult;
    }


    private static Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + SECURITY_KEY; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }

}
