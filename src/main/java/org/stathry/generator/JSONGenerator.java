package org.stathry.generator;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * 生成json（用于测试传参等）
 */
public class JSONGenerator {

    private static final String DEFAULT_VALUE= "";

    private JSONGenerator() {}

    public static String generateJsonString(String arrayString, boolean escape) {
        if(StringUtils.isBlank(arrayString)) {
            return "";
        }
        String[] arr = null;
        if(arrayString.indexOf(',') != -1) {
            arr = arrayString.split(",");
        }
        else if(arrayString.indexOf('|') != -1) {
            arr = arrayString.split("|");
        }
        return generateJsonString(arr, escape);
    }
    
    public static String generateJsonString(String[] arr, boolean escape) {
        if(arr == null || arr.length == 0) {
            return null;
        }
        
        Map<String, String> data = new LinkedHashMap<>();
        for (String s : arr) {
            if(StringUtils.isNotBlank(s)) {
                data.put(s, DEFAULT_VALUE);
            }
        }
        
        String json = JSON.toJSONString(data);
        return escape ? escapeJSONString(json) : json;
    }
    
    /** 转义 
     * @param json
     * @return
     */
    public static String escapeJSONString(String json) {
        return StringEscapeUtils.ESCAPE_JSON.translate(json);
    }
}
