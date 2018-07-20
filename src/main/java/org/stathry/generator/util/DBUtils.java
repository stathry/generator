package org.stathry.generator.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
 * 数据库访问工具类:获取表结构信息等
 * @author dongdaiming
 */
public class DBUtils {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DBUtils.class);
    
    private static final boolean COLLUMN_CONVERT = true;

    private static final Set<String> EX_COLS = ConfigManager.getObjectByResource("template", "orm.insert.exclude.columns", Set.class);
    
    private DBUtils() {}
    
    public static List<BeanInfo> getTableInfoList(Connection conn, JdbcTemplate template, String schema) throws SQLException {
        return getTableInfoList(conn, template, schema, null);
    }
    public static List<BeanInfo> getTableInfoList(Connection conn, JdbcTemplate template, String schema, List<String> tableNames) throws SQLException {
        DatabaseMetaData db = conn.getMetaData();
        LOGGER.info("database version {} {}.", db.getDatabaseProductName(), db.getDatabaseProductVersion());

        boolean isAll = tableNames == null || tableNames.isEmpty();
        List<Map<String, Object>> tables;
        if(isAll) {
            tables= template.queryForList("select table_name, table_comment from information_schema.tables where table_schema=?", schema);
        } else {
            tables= template.queryForList("select table_name, table_comment from information_schema.tables where table_schema=? and table_name in " + concatInTables(tableNames), schema);
        }

        if(tables == null || tables.isEmpty()) {
            return null;
        }
        List<BeanInfo> beans = new ArrayList<BeanInfo>(tables.size());
        BeanInfo bean;
        List<Map<String, Object>> columns;
        List<FieldInfo> fields;
        List<FieldInfo> iFields;
        FieldInfo field;
        String curTable;
        for(Map<String, Object> t : tables) {
            bean = initBeanInfoFromTable(t);
            beans.add(bean);
            curTable = bean.getTable();
            if((columns = queryColumns(template, curTable)) == null || columns.isEmpty()) {
                continue;
            }

            fields = new ArrayList<>(columns.size());
            bean.setFields(fields);
            iFields = new ArrayList<>(4);
            bean.setInsertFields(iFields);

            for(Map<String, Object> c : columns) {
                field = initFieldInfoFromColumn(c);
                fillFields(fields, iFields, field, bean);
            }
        }
        return beans;
    }

    private static void fillFields(List<FieldInfo> fields, List<FieldInfo> iFields, FieldInfo field, BeanInfo bean) {
        if(field.getColumn().equalsIgnoreCase("id")) {
            bean.setIdType(field.getType());
            bean.setIdJdbcType(field.getJdbcType());
            fields.add(0, field);
        } else {
            fields.add(field);
        }
        if(!EX_COLS.contains(field.getColumn())) {
            iFields.add(field);
        }
    }

    private static FieldInfo initFieldInfoFromColumn(Map<String, Object> c) {
        FieldInfo field;
        String type;
        int index;
        field = new FieldInfo();
        field.setColumn(String.valueOf(c.get("column_name")).toLowerCase());
        field.setName(columnToField(field.getColumn()));
        type = String.valueOf(c.get("column_type"));
        index = type.indexOf('(');
        type = index != -1 ? type.substring(0, type.indexOf('(')) : type;
        field.setType(columnToField(DBDataTypeEnums.getTypeByName(type)));
        field.setJdbcType(type.toUpperCase());
        field.setComment(columnToField(String.valueOf(c.get("column_comment"))));
        return field;
    }

    private static List<Map<String,Object>> queryColumns(JdbcTemplate template, String curTable) {
        return template.queryForList("select column_name,column_type,column_comment from INFORMATION_SCHEMA.Columns where table_name=?", curTable);
    }

    private static BeanInfo initBeanInfoFromTable(Map<String,Object> t) {
        BeanInfo bean = new BeanInfo();
        String curTable = (String)t.get("table_name");
        bean.setTable(curTable);
        bean.setClzz(StringUtils.capitalize(columnToField(curTable)));
        bean.setIdType("long");
        bean.setIdJdbcType("BIGINT");
        String comment = (String) t.get("table_comment");
        int n;
        if(comment != null && comment.length() > 0) {
            if((n = comment.indexOf("表")) != -1 && n > 1) {
                comment = comment.substring(0, n - 1);
            }
        } else {
            comment = "";
        }
        bean.setDesc(comment);
        return bean;
    }

    private static String concatInTables(List<String> tableNames) {
        StringBuilder builder = new StringBuilder("(");
        for (int i = 0, size = tableNames.size(); i < size; i++) {
            if(i != 0) {
            builder.append(", '");
            } else {
                builder.append("'");
            }
            builder.append(tableNames.get(i));
            builder.append("'");
        }
        return builder.append(')').toString();
    }

    public static BeanInfo getTableInfo(Connection conn, JdbcTemplate template, String schema, String tableName) throws SQLException {
        if(StringUtils.isBlank(tableName)) {
            return null;
        }
        return getTableInfoList(conn, template, schema, Arrays.asList(tableName)).get(0);
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
        return StringUtils.uncapitalize(temp.toString());
    }
    
}
