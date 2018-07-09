package org.stathry.generator;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.stathry.generator.JSONGenerator;
import org.stathry.generator.model.FieldInfo;

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

    @Test
    public void test4() {
        JSONObject data = new JSONObject();
        String s = "{\"status\":200,\"msg\":\"正常\",\"data\":{\"black_summary\":[{\"black_type\":\"逾期\",\"item_name\":\"最早出现时间\",\"label\":\"YQ001\",\"item_value\":\"2016-03-2500:00:00\",\"remarks\":\"\"},{\"black_type\":\"逾期\",\"item_name\":\"最近出现时间\",\"label\":\"YQ002\",\"item_value\":\"2017-09-0900:00:00\",\"remarks\":\"\"},{\"black_type\":\"逾期\",\"item_name\":\"累计出现次数\",\"label\":\"YQ003\",\"item_value\":\"3\",\"remarks\":\"\"},{\"black_type\":\"逾期\",\"item_name\":\"当前逾期金额\",\"label\":\"YQ004\",\"item_value\":\"3000~4000\",\"remarks\":\"\"},{\"black_type\":\"逾期\",\"item_name\":\"当前逾期时长\",\"label\":\"YQ005\",\"item_value\":\"M2\",\"remarks\":\"\"},{\"black_type\":\"逾期\",\"item_name\":\"历史最大逾期金额\",\"label\":\"YQ006\",\"item_value\":\"1000~2000\",\"remarks\":\"\"},{\"black_type\":\"逾期\",\"item_name\":\"历史最大逾期时长\",\"label\":\"YQ007\",\"item_value\":\"M0\",\"remarks\":\"\"}]}}";
        data.put("A102", JSON.parseObject(s));
        System.out.println(data);
    }

    @Test
    public void test5() {
        JSONObject data = new JSONObject();
        String s = "{\"msg\":\"正常\",\"data\":[{\"execution\":\"全部未履行\",\"reason\":\"其他有履行能力而拒不履行生效法律文书确定义务\",\"case_no\":\"（2016）苏****执1**号\",\"sex\":\"男\",\"person_name\":\"张**\",\"court\":\"南京市秦淮区人民法院\",\"execution_unit\":\"南京市秦淮区人民法院\",\"id_no\":\"3305111963****2037\",\"province\":\"江苏\",\"document_no\":\"(2015)秦民初字第0***3号\",\"obligation\":\"(2015)秦民初字第0***3号\",\"publish_time\":\"2016-06-0300:00:00\",\"time\":\"2016-03-1000:00:00\",\"person_concerned\":\"\",\"age\":53}],\"status\":200}";
        data.put("A301", JSON.parseObject(s));
        System.out.println(data);
    }

}
