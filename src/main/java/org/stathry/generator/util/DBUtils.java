package org.stathry.generator.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.stathry.generator.enums.DBDataTypeEnums;
import org.stathry.generator.model.BeanInfo;
import org.stathry.generator.model.FieldInfo;

/**
 * TODO
 * @author dongdaiming
 */
public class DBUtils {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DBUtils.class);
    
    private static final boolean COLLUMN_CONVERT = true;
    
    private DBUtils() {}
    
    public static List<BeanInfo> getTableInfoList(Connection conn, JdbcTemplate template, String schema) throws SQLException {
        DatabaseMetaData db = conn.getMetaData();
        LOGGER.info("database version {}{}.", db.getDatabaseProductName(), db.getDatabaseProductVersion());
        List<Map<String, Object>> tables = template.queryForList(
                "select table_name, table_comment from information_schema.tables where table_schema=?", schema);
        if(tables == null || tables.isEmpty()) {
            return Collections.emptyList();
        }
        List<BeanInfo> beans = new ArrayList<BeanInfo>(tables.size());
        BeanInfo bean;
        String tableName;
        List<Map<String, Object>> columns;
        List<FieldInfo> fields;
        FieldInfo field;
        String type;
        int index;
        for(Map<String, Object> t : tables) {
            bean = new BeanInfo();
            beans.add(bean);
            tableName = String.valueOf(t.get("table_name"));
            bean.setClzz(StringUtils.capitalize(columnToField(tableName)));
            if(t.get("table_comment") != null) {
                bean.setDesc(String.valueOf(t.get("table_comment")).replaceAll("表", ""));
            }
            
            columns = template.queryForList(
                    "select column_name,column_type,column_comment from INFORMATION_SCHEMA.Columns where table_name=?",
                    tableName);
            if(columns == null || columns.isEmpty()) {
                continue;
            }
            
            fields = new ArrayList<FieldInfo>(columns.size());
            bean.setFields(fields);
            for(Map<String, Object> c : columns) {
                field = new FieldInfo();
                field.setName(columnToField(String.valueOf(c.get("column_name"))));
                type = String.valueOf(c.get("column_type"));
                index = type.indexOf('(');
                type = index != -1 ? type.substring(0, type.indexOf('(')) : type;
                field.setType(columnToField(DBDataTypeEnums.getTypeByName(type)));
                field.setComment(columnToField(String.valueOf(c.get("column_comment"))));
                fields.add(field);
            }
        }
        return beans;
    }
    
    public static BeanInfo getTableInfo(Connection conn, JdbcTemplate template, String schema, String tableName) throws SQLException {
        DatabaseMetaData db = conn.getMetaData();
        LOGGER.info("database version {}{}.", db.getDatabaseProductName(), db.getDatabaseProductVersion());
        List<Map<String, Object>> tables = template.queryForList(
                "select table_name, table_comment from information_schema.tables where table_schema=? and table_name = ?", schema, tableName);
        if(tables == null || tables.isEmpty()) {
            return null;
        }
        List<BeanInfo> beans = new ArrayList<BeanInfo>(tables.size());
        BeanInfo bean;
        List<Map<String, Object>> columns;
        List<FieldInfo> fields;
        FieldInfo field;
        String type;
        int index;
        for(Map<String, Object> t : tables) {
            bean = new BeanInfo();
            beans.add(bean);
            bean.setClzz(StringUtils.capitalize(columnToField(tableName)));
            if(t.get("table_comment") != null) {
                bean.setDesc(String.valueOf(t.get("table_comment")).replaceAll("表", ""));
            }
            
            columns = template.queryForList(
                    "select column_name,column_type,column_comment from INFORMATION_SCHEMA.Columns where table_name=?",
                    tableName);
            if(columns == null || columns.isEmpty()) {
                continue;
            }
            
            fields = new ArrayList<FieldInfo>(columns.size());
            bean.setFields(fields);
            for(Map<String, Object> c : columns) {
                field = new FieldInfo();
                field.setCollumn(String.valueOf(c.get("column_name")));
                field.setName(columnToField(field.getCollumn()));
                type = String.valueOf(c.get("column_type"));
                index = type.indexOf('(');
                type = index != -1 ? type.substring(0, type.indexOf('(')) : type;
                field.setType(columnToField(DBDataTypeEnums.getTypeByName(type)));
                field.setComment(columnToField(String.valueOf(c.get("column_comment"))));
                fields.add(field);
            }
        }
        return beans.get(0);
    }
    
    public static void printTableInfo(Connection conn, JdbcTemplate template, String schema, String tableName) throws SQLException {
        DatabaseMetaData db = conn.getMetaData();
        LOGGER.info("database version {}{}.", db.getDatabaseProductName(), db.getDatabaseProductVersion());
        List<Map<String, Object>> tables = template.queryForList(
                "select table_name, table_comment from information_schema.tables where table_schema=? and table_name = ?", schema, tableName);
        if(tables == null || tables.isEmpty()) {
            return;
        }
        List<Map<String, Object>> columns;
        Set<String> names = new LinkedHashSet<>();
        Set<String> comments = new LinkedHashSet<>();
        columns = template.queryForList(
                        "select column_name,column_type,column_comment from INFORMATION_SCHEMA.Columns where table_name=?",
                        tableName);
        for (Map<String, Object> c : columns) {
            names.add(String.valueOf(c.get("column_name")));
            comments.add(String.valueOf(c.get("column_comment")));
        }
        System.out.println(StringUtils.join(names, ","));
        System.out.println(StringUtils.join(comments, ","));
    }
    
    public static String dbTypeToJava(String dbType) {
        return "";
    }
    
    public static List<String> fieldsToColumns(List<String> columns) {
        if(columns == null || columns.isEmpty()) {
            return Collections.emptyList();
        }
        
        List<String> fields = new ArrayList<>(columns.size());
        for (String c : columns) {
            fields.add(columnToField(c));
        }
        return fields;
    }

    public static String columnToField(String c) {
        if(StringUtils.isBlank(c)) {
            return "";
        }
        if(!COLLUMN_CONVERT) {
        	return c;
        }
        if(!c.contains("_")) {
            return c;
        }
        String[] a = c.split("_");
        StringBuilder temp = new StringBuilder(a[0]);
        for(int i = 1; i < a.length; i++) {
            temp.append(StringUtils.capitalize(a[i]));
        }
        return temp.toString();
    }
    
}
