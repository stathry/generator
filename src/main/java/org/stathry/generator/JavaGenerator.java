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
import org.stathry.generator.model.ORMTemplateContext;
import org.stathry.generator.util.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 */
@Component
public class JavaGenerator {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaGenerator.class);
    
    private Lock lock = new ReentrantLock();
    
    @Autowired 
    private ORMTemplateContext templateContext;
    @Value("${jdbc.schema}")
    private String schema;
    @Value("${orm.template.timePattern}")
    private String timePattern;
    
    public void generateByTemplate(BeanInfo beanInfo) throws Exception {
        generateByTemplate(beanInfo, false);
    }
    public void generateByTemplate(BeanInfo beanInfo, boolean hasColumnAnnotation) throws Exception {
        ORMTemplateContext tc = templateContext;
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new ClassPathResource(tc.getTemplateDir()).getFile());
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
        String templateName = hasColumnAnnotation ? tc.getJpaTemplateName() : tc.getMybatisTemplateName();
        Template template = cfg.getTemplate(templateName);

        tc.setGenerateTime(DateFormatUtils.format(new Date(), timePattern));
        lock.lock();
        Writer out = null;
        try {
        	tc.setTable(beanInfo.getTable());
            tc.setFields(beanInfo.getFields());
            tc.setClzz(beanInfo.getClzz());
            tc.setDesc(beanInfo.getDesc());
            File file = new File(tc.getTargetPath()  + schema + "/model/" + beanInfo.getClzz() + ".java");
            FileUtils.createFile(file);
            LOGGER.info("java file has been generated, path is \"{}\".", file.getAbsolutePath());
            out = new FileWriter(file);
//      Writer out = new OutputStreamWriter(System.out);  
            template.process(tc, out);
            out.flush();  
        } catch(Exception e) {
            LOGGER.error("generateJavaBean error," + e.getMessage(), e);
        } finally {
            lock.unlock();
            if(out != null) {
                out.close();
            }
        }
    }
    
}
