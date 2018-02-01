package org.free.commons.generator.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.free.commons.generator.enums.DataTypeEnums;
import org.free.commons.generator.enums.ExcelTypeEnums;
import org.free.commons.generator.model.FieldInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 */
public class ExcelUtils {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);
    
    private ExcelUtils() {}

    /**
     * 读取excel数据到map(以首行为key)
     * @param path
     * @return
     */
    public static List<Map<String, String>> readToMap(String path) {
        if (StringUtils.isBlank(path)) {
            LOGGER.warn("invalid excel path.");
            return Collections.emptyList();
        }
        String type = FilenameUtils.getExtension(path);
        if (!ExcelTypeEnums.xls.name().equalsIgnoreCase(type) 
                && !ExcelTypeEnums.xlsx.name().equalsIgnoreCase(type)) {
            LOGGER.warn("invalid excel type {}, path {}.", type, path);
            return Collections.emptyList();
        }
        
        InputStream in = null;
        Workbook book = null;
        try {
            in = new FileInputStream(path);
            book = WorkbookFactory.create(in);
            Iterator<Sheet> it = book.sheetIterator();
            Sheet sheet = null;
            while(it.hasNext()) {
                sheet = it.next();
                if(sheet != null) {
                    break;
                }
            }
            List<String> keys = new ArrayList<>(); 
            List<Map<String, String>> data = new ArrayList<>();
            
            Row fRow = sheet.getRow(sheet.getFirstRowNum());
            Row row;
            Cell cell;
            for(int iCell = fRow.getFirstCellNum(); iCell <= fRow.getLastCellNum(); iCell++) {
                cell = fRow.getCell(iCell);
                if(cell == null) {
                    continue;
                }
                keys.add(StringUtils.trimToEmpty(cell.getStringCellValue()));
            }
            
            int keySize = keys.size();
            int iRow = sheet.getFirstRowNum() + 1;
            Map<String, String> map;

            for(; iRow <= sheet.getLastRowNum(); iRow++) {
                row = sheet.getRow(iRow);
                if(row == null) {
                    continue;
                }
                map = new HashMap<>(keySize);
                for(int iCell = fRow.getFirstCellNum(), i = 0; iCell <= fRow.getLastCellNum(); iCell++,i++) {
                    cell = row.getCell(iCell);
                    if(cell == null) {
                        continue;
                    }
                    map.put(keys.get(i), StringUtils.trimToEmpty(cell.getStringCellValue()));
                }
                data.add(map);
            }
            return data;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if(book != null) {
                try {
                    book.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }
    
    /**
     * 读取excel数据到map(以首行为key)
     * @param path
     * @return
     */
    public static List<FieldInfo> readToBean(String path) {
        if (StringUtils.isBlank(path)) {
            LOGGER.warn("invalid excel path.");
            return Collections.emptyList();
        }
        String type = FilenameUtils.getExtension(path);
        if (!ExcelTypeEnums.xls.name().equalsIgnoreCase(type) 
                && !ExcelTypeEnums.xlsx.name().equalsIgnoreCase(type)) {
            LOGGER.warn("invalid excel type {}, path {}.", type, path);
            return Collections.emptyList();
        }
        
        InputStream in = null;
        Workbook book = null;
        try {
            in = new FileInputStream(path);
            book = WorkbookFactory.create(in);
            Iterator<Sheet> it = book.sheetIterator();
            Sheet sheet = null;
            while(it.hasNext()) {
                sheet = it.next();
                if(sheet != null) {
                    break;
                }
            }
            List<FieldInfo> fields = new ArrayList<>();
            
            Row row;
            int iRow = sheet.getFirstRowNum();
            FieldInfo field;
            String dataType;
            for(; iRow <= sheet.getLastRowNum(); iRow++) {
                row = sheet.getRow(iRow);
                if(row == null) {
                    continue;
                }
                field = new FieldInfo();
                field.setName(StringUtils.trimToEmpty(row.getCell(0).getStringCellValue()));
                dataType = DataTypeEnums.getTypeByName(StringUtils.trimToEmpty(row.getCell(1).getStringCellValue()));
                field.setType(dataType);
                field.setComment(StringUtils.trimToEmpty(row.getCell(2).getStringCellValue()));
                fields.add(field);
            }
            return fields;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if(book != null) {
                try {
                    book.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

}
