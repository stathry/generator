package org.free.commons.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.free.commons.generator.enums.DataTypeEnums;

/**
 * 生成参数定义
 */
public class FieldGenerator {
    
    private static final String[] KEYS = {"name", "type", "desc"}; 

    public static String generateFieldBlocks(List<Map<String, String>> fieldMaps) {
        if (fieldMaps == null || fieldMaps.isEmpty()) {
            return "";
        }
        StringBuilder temp = new StringBuilder();
        for (Map<String, String> map : fieldMaps) {
            temp.append("/** ");
            temp.append(map.get(KEYS[2]));
            temp.append(" */");
            temp.append(SystemUtils.LINE_SEPARATOR);
            temp.append("private ");
            temp.append(DataTypeEnums.getTypeByName(map.get(KEYS[1])));
            temp.append(" ");
            temp.append(map.get(KEYS[0]));
            temp.append(";");
            temp.append(SystemUtils.LINE_SEPARATOR);
        }
        return temp.toString();
    }
    
    public static void generateByStringArray(String s, boolean comment) throws IOException{
        String[] a = s.split(",");
        StringBuilder temp = new StringBuilder();
        for (String e : a) {
            if(comment) {
                temp.append("/** ");
                temp.append("");
                temp.append(" */");
            }
            temp.append(SystemUtils.LINE_SEPARATOR);
            temp.append("private ");
            temp.append("String");
            temp.append(" ");
            temp.append(e);
            temp.append(";");
            temp.append(SystemUtils.LINE_SEPARATOR);
        }
        System.out.println();
        System.out.println();
        System.out.println(temp.toString());
        System.out.println();
        System.out.println();
    }
    
}
