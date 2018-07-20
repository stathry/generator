package org.stathry.generator.util;

import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.formula.functions.T;

import java.util.ResourceBundle;

/**
 * 配置管理
 * Created by dongdaiming on 2018-06-28 16:15
 */
public class ConfigManager {

    private ConfigManager() {}

    private static final String BASE_RESOURCE_NAME = "conf/";
    private static final ResourceBundle DEFAULT_BUNDLE = ResourceBundle.getBundle(BASE_RESOURCE_NAME + "config");

    public static String get(String key) {
        return DEFAULT_BUNDLE.getString(key);
    }
    public static String getString(String key) {
        return DEFAULT_BUNDLE.getString(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(DEFAULT_BUNDLE.getString(key));
    }

    public static long getLong(String key) {
        return Long.parseLong(DEFAULT_BUNDLE.getString(key));
    }

    public static <T> T getObject(String key, Class<T> clazz) {
        return JSON.parseObject(DEFAULT_BUNDLE.getString(key) ,clazz);
    }

    public static <T> T getObjectByResource(String resourceName, String key, Class<T> clazz) {
        return JSON.parseObject(ResourceBundle.getBundle(BASE_RESOURCE_NAME + resourceName).getString(key) ,clazz);
    }

    public static String getByResource(String resourceName, String key) {
        return ResourceBundle.getBundle(BASE_RESOURCE_NAME + resourceName).getString(key);
    }

}
