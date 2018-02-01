package org.free.commons.generator;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sql.DataSource;

import org.bryadong.generator.JavaGenerator;
import org.bryadong.generator.model.BeanInfo;
import org.bryadong.generator.model.FieldInfo;
import org.bryadong.generator.util.DBUtils;
import org.bryadong.generator.util.ExcelUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    public void testGenerateByTemplateFromExcel() throws IOException, TemplateException, InterruptedException {
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
                    List<FieldInfo> fields = ExcelUtils.readToBean("D:\\doc\\temp.xlsx");
                    beanInfo.setFields(fields);
                    try {
                        javaGenerator.generateByTemplate(beanInfo);
                    } catch (IOException | TemplateException e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                }
            });
        }
        latch.await();
    }
    
    @Test
    public void testGenerateByTemplateFromDB() throws IOException, TemplateException, SQLException {
        Connection conn = dataSource.getConnection();
        List<BeanInfo> list = DBUtils.getTableInfoList(conn, jdbcTemplate, schema);
        for (BeanInfo beanInfo : list) {
            javaGenerator.generateByTemplate(beanInfo);
        }
        System.out.println(list);
    }
    @Test
    public void testGenerateByTemplateFromDB2() throws IOException, TemplateException, SQLException {
        Connection conn = dataSource.getConnection();
        BeanInfo bean = DBUtils.getTableInfo(conn, jdbcTemplate, schema, "zixin_report_doc");
            javaGenerator.generateByTemplate(bean);
        System.out.println(bean);
    }
    
    @Test
    public void testPrintTableInfo() throws IOException, TemplateException, SQLException {
        Connection conn = dataSource.getConnection();
        DBUtils.printTableInfo(conn, jdbcTemplate, schema, "wangxin_sync_doc");
    }
    
}
