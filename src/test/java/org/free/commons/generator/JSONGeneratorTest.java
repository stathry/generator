package org.free.commons.generator;

import org.bryadong.generator.JSONGenerator;
import org.bryadong.generator.model.FieldInfo;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;


/**
 * TODO
 */
public class JSONGeneratorTest {

    @Test
    public void test1() {
        String s = "user_name,user_credentials_type,user_credentials_no,order_no,biz_type,order_status,create_amt,pay_month,gmt_ovd_date,overdue_days,overdue_amt,gmt_pay,memo";
        String json = JSONGenerator.generateJsonString(s, false);
        System.out.println(json);
    }
    
    @Test
    public void test2() {
        String s = "user_name,user_credentials_type,user_credentials_no,order_no,biz_type,order_status,create_amt,pay_month,gmt_ovd_date,overdue_days,overdue_amt,gmt_pay,memo";
        String json = JSONGenerator.generateJsonString(s, true);
        System.out.println(json);
//        String s2 = "{\"user_name\":\"abcdef\",\"user_credentials_type\":\"abcdef\",\"user_credentials_no\":\"abcdef\",\"order_no\":\"abcdef\",\"biz_type\":\"abcdef\",\"order_status\":\"abcdef\",\"create_amt\":\"abcdef\",\"pay_month\":\"abcdef\",\"gmt_ovd_date\":\"abcdef\",\"overdue_days\":\"abcdef\",\"overdue_amt\":\"abcdef\",\"gmt_pay\":\"abcdef\",\"memo\":\"abcdef\"}";
//        System.out.println();
//        System.out.println();
//        System.out.println(s2);
    }
    
    @Test
    public void test3() {
        FieldInfo f = new FieldInfo();
        System.out.println(JSON.toJSONString(f));
        System.out.println(JSON.toJSONString(f, SerializerFeature.WriteMapNullValue));
        System.out.println(JSON.toJSONString(f, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
    }

}
