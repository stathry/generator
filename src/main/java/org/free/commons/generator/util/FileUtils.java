/**
 * Copyright 2012-2016 free Co., Ltd.
 */
package org.free.commons.generator.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

/**
 * @author dongdaiming@free.com
 *
 * 2016年8月22日
 */
public class FileUtils {
    
    private FileUtils() {}
	
	public static boolean exists(String path) {
		if(StringUtils.isBlank(path)) {
			return false;
		}
		return new File(path).exists();
	}
	
	public static boolean mkParentDirs(String path) {
		if(StringUtils.isBlank(path)) {
			return false;
		}
		File file = new File(path);
		if(file.exists()) {
		    return true;
		}
		File parent = file.getParentFile();
		if(parent == null) {
		    return false;
		}
		return parent.exists() ? true : parent.mkdirs();
	}
	
	   public static boolean createFile(String path) throws IOException {
	        if(StringUtils.isBlank(path)) {
	            return false;
	        }
	        File file = new File(path);
	        return createFile(file);
	    }
	   
	   public static boolean createFile(File file) throws IOException {
	       if(file == null) {
	           return false;
	       }
	       if(file.exists()) {
	           return true;
	       }
	       File parent = file.getParentFile();
	       if(!parent.exists()) {
	           parent.mkdirs();
	       }
	       return file.createNewFile();
	   }
	   
	   public static void main(String[] args) {
        boolean b = mkParentDirs("/temp3");
        System.out.println(b);
    }

}
