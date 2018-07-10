package org.stathry.generator;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.stathry.generator.model.BeanInfo;
import org.stathry.generator.model.FieldInfo;
import org.stathry.generator.model.ORMTemplateContext;
import org.stathry.generator.util.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;

/**
 * TODO
 * Created by dongdaiming on 2018-07-10 09:54
 */
@Component
public class MapperGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapperGenerator.class);

    @Autowired
    private ORMTemplateContext templateContext;

    @Value("${jdbc.schema}")
    private String schema;
    @Value("${orm.template.mapperTemplateName}")
    private String mapperTempName;
    @Value("${orm.template.timePattern}")
    private String timePattern;

    public void generateMapperByTemplate(BeanInfo beanInfo) throws Exception {
        ORMTemplateContext tc = templateContext;
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new ClassPathResource(tc.getTemplateDir()).getFile());
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
        Template template = cfg.getTemplate(mapperTempName);

            tc.setTable(beanInfo.getTable());
            tc.setFields(beanInfo.getFields());
            tc.setClzz(beanInfo.getClzz());
            tc.setDesc(beanInfo.getDesc());
            tc.setIdJdbcType(beanInfo.getIdJdbcType());
            tc.setIdType(beanInfo.getIdType().toLowerCase());
            tc.setInsertFields(beanInfo.getInsertFields());
            tc.setGenerateTime(DateFormatUtils.format(new Date(), timePattern));
        Writer out = null;
        try {
            File file = new File(tc.getTargetPath() + schema + "/mapper/" + beanInfo.getClzz() + "Mapper.xml");
            FileUtils.createFile(file);
            LOGGER.info("mapper file has been generated, path is \"{}\".", file.getAbsolutePath());
            out = new FileWriter(file);
            template.process(tc, out);
            out.flush();
        } catch(Exception e) {
            LOGGER.error("generate mapper error," + e.getMessage(), e);
        } finally {
            if(out != null) {
                out.close();
            }
        }
    }


}
