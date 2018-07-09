package org.stathry.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.stathry.generator.enums.DataTypeEnums;
import org.stathry.generator.model.BeanInfo;
import org.stathry.generator.model.JavaTemplateConfig;
import org.stathry.generator.util.FileUtils;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * TODO
 */
@Component
public class JavaGenerator {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaGenerator.class);
    
    private Lock lock = new ReentrantLock();
    
    @Autowired 
    private JavaTemplateConfig templateConfig;
    
    public void generateByTemplate(BeanInfo beanInfo) throws Exception {
        generateByTemplate(beanInfo, false);
    }
    public void generateByTemplate(BeanInfo beanInfo, boolean hasColumnAnnotation) throws Exception {
        JavaTemplateConfig templateConfig1 = templateConfig;
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new ClassPathResource(templateConfig1.getTemplateDir()).getFile());
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
        String templateName = hasColumnAnnotation ? templateConfig1.getJpaTemplateName() : templateConfig1.getMybatisTemplateName();
        Template template = cfg.getTemplate(templateName);
        
        lock.lock(); 
        try {
        	templateConfig1.setTable(beanInfo.getTable());
            templateConfig1.setFields(beanInfo.getFields());
            templateConfig1.setClzz(beanInfo.getClzz());
            templateConfig1.setDesc(beanInfo.getDesc());
            File file = new File(templateConfig1.getTargetPath() + beanInfo.getClzz() + ".java");
            FileUtils.createFile(file);
            System.out.println();
            LOGGER.info("java file has been generated, path is \"{}\".", file.getAbsolutePath());
            System.out.println();
            Writer out = new FileWriter(file);
//      Writer out = new OutputStreamWriter(System.out);  
            template.process(templateConfig1, out);
            out.flush();  
        } catch(Exception e) {
            LOGGER.error("generateJavaBean error," + e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }
    
}
