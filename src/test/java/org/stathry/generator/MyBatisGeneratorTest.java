package org.stathry.generator;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.stathry.generator.enums.DAOType;
import org.stathry.generator.model.BeanInfo;
import org.stathry.generator.util.ConfigManager;
import org.stathry.generator.util.DBUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Mybatis相关文件生成测试
 *
 * @author stathry
 * @date 2018/3/24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class MyBatisGeneratorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisGeneratorTest.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JavaGenerator javaGenerator;

    @Autowired
    private MapperGenerator mapperGenerator;

    @Autowired
    private DAOGenerator daoGenerator;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${jdbc.schema}")
    private String schema;

    /** 生成所有表的model,mapper和DAO文件 */
    @Test
    public void testSmartGeneratorWithDAO() throws Exception {
        copyContextUtilsFile();
        Connection conn = dataSource.getConnection();
        List<BeanInfo> beans = DBUtils.getTableInfoList(conn, jdbcTemplate, schema);
        daoGenerator.generateDAOByTemplate(null, DAOType.GEN_DAO);
        daoGenerator.generateDAOByTemplate(null, DAOType.GEN_DAO_IMPL);
        LOGGER.info("found {} tables in database {}.", beans.size(), schema);
        for (BeanInfo bean : beans) {
            mapperGenerator.generateMapperByTemplate(bean);
            System.out.println(bean);
            javaGenerator.generateByTemplate(bean, false);
            daoGenerator.generateDAOByTemplate(bean, DAOType.DAO);
            daoGenerator.generateDAOByTemplate(bean, DAOType.DAO_IMPL);
        }
    }

    /** 生成所有表的model和mapper文件 */
    @Test
    public void testSmartGenerator() throws Exception {
        Connection conn = dataSource.getConnection();
        List<BeanInfo> beans = DBUtils.getTableInfoList(conn, jdbcTemplate, schema);
        LOGGER.info("found {} tables in database {}.", beans.size(), schema);
        for (BeanInfo bean : beans) {
            mapperGenerator.generateMapperByTemplate(bean);
            System.out.println(bean);
            javaGenerator.generateByTemplate(bean, false);
        }
    }

    @Test
    public void testSmartGenerateByTables() throws Exception {
        Connection conn = dataSource.getConnection();
        // 指定表名
        List<String> tables = Arrays.asList("risk_mock_task");
        List<BeanInfo> beans = DBUtils.getTableInfoList(conn, jdbcTemplate, schema, tables);
        LOGGER.info("found {} tables in database {}.", beans.size(), schema);
        for (BeanInfo bean : beans) {
            mapperGenerator.generateMapperByTemplate(bean);
            System.out.println(bean);
            javaGenerator.generateByTemplate(bean, false);
        }
    }

    @Test
    public void testGenerateMapperByTemplate() throws Exception {
        Connection conn = dataSource.getConnection();
        List<String> tables = Arrays.asList("log_his_201807");
        BeanInfo bean;
        for (String t : tables) {
            bean = DBUtils.getTableInfo(conn, jdbcTemplate, schema, t);
            if(bean == null) {
                LOGGER.warn("not found table {}.", t);
                continue;
            }
            bean.setTable(t);
            mapperGenerator.generateMapperByTemplate(bean);
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

    // 通过原生的MyBatisGenerator生成(缺点是生成的冗余信息较多,难以定制化)
    @Test
    public void testGenerator() throws Exception{
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //指定 逆向工程配置文件
        File configFile = new ClassPathResource("generatorConfig.xml").getFile();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);

    }

    private void copyContextUtilsFile() {
        String javaPath = SystemUtils.getUserDir() + "/src/main/java/org/stathry/generator/util/ApplicationContextUtils.java";
        String basePath = ConfigManager.getByResource("template", "orm.template.targetPath");
        File f = new File(basePath + schema + "/ApplicationContextUtils.java");
        f.getParentFile().mkdirs();
        try {
            System.out.println(IOUtils.copy(new FileInputStream(javaPath), new FileOutputStream(f)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
