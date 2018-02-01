package org.free.commons.generator.util;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * TODO
 */
public class ExcelUtilsTest {

    @Test
    public void test() {
        List<Map<String, String>> list = ExcelUtils.readToMap("D:\\doc\\temp.xlsx");
        System.out.println(list);
        System.out.println(list.size());
    }

}
