package org.stathry.generator;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.stathry.generator.JavaGenerator;
import org.stathry.generator.model.BeanInfo;
import org.stathry.generator.model.FieldInfo;
import org.stathry.generator.util.DBUtils;
import org.stathry.generator.util.ExcelUtils;

import freemarker.template.TemplateException;

/**
 * TODO
 * 
 * @author dongdaiming
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class JavaGeneratorTest {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JavaGenerator javaGenerator;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Value("${jdbc.schema}")
    private String schema;
    
    @Test
    public void testGenerateByTemplateFromExcel() throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(1000);
        for(int i = 0; i < 1000; i++) {
            final int j = i;
            service.submit(new Runnable() {
                
                @Override
                public void run() {
                    BeanInfo beanInfo = new BeanInfo();
                    beanInfo.setClzz("Bean" + j);
                    beanInfo.setDesc("desc" + j);
                    List<FieldInfo> fields = ExcelUtils.readToBean("/template/temp.xlsx");
                    beanInfo.setFields(fields);
                    try {
                        javaGenerator.generateByTemplate(beanInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                }
            });
        }
        latch.await();
    }
    
    @Test
    public void testGenerateByTemplateFromDB() throws Exception {
        Connection conn = dataSource.getConnection();
        List<BeanInfo> list = DBUtils.getTableInfoList(conn, jdbcTemplate, schema);
        for (BeanInfo beanInfo : list) {
            javaGenerator.generateByTemplate(beanInfo);
        }
        System.out.println(list);
    }

	@Test
	public void testGenerateJPAModelByTemplateFromDB() throws Exception {
		Connection conn = dataSource.getConnection();
		List<String> tables = Arrays.asList("log_his_201806");
		for(String t : tables) {
			BeanInfo bean = DBUtils.getTableInfo(conn, jdbcTemplate, schema, t);
			bean.setTable(t);
			javaGenerator.generateByTemplate(bean, true);
			System.out.println(bean);
		}
	}

	@Test
	public void testGenerateModelByTemplateFromDB() throws Exception {
		Connection conn = dataSource.getConnection();
		List<String> tables = Arrays.asList("log_his_201807");
		for(String t : tables) {
			BeanInfo bean = DBUtils.getTableInfo(conn, jdbcTemplate, schema, t);
			bean.setTable(t);
			javaGenerator.generateByTemplate(bean, false);
			System.out.println(bean);
		}
	}
    
    @Test
    public void testPrintTableInfo() throws Exception {
        Connection conn = dataSource.getConnection();
        DBUtils.printTableInfo(conn, jdbcTemplate, schema, "routing_option");
    }
    
}
