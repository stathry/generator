package org.free.commons.generator;

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
import org.free.commons.generator.enums.DataTypeEnums;
import org.free.commons.generator.model.BeanInfo;
import org.free.commons.generator.model.JavaTemplateConfig;
import org.free.commons.generator.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

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
    
    public void generateByTemplate(BeanInfo beanInfo) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new ClassPathResource(templateConfig.getTemplateDir()).getFile());
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
        Template template = cfg.getTemplate(templateConfig.getTemplateName());
        
        lock.lock(); 
        try {
            templateConfig.setFields(beanInfo.getFields());
            templateConfig.setClzz(beanInfo.getClzz());
            templateConfig.setDesc(beanInfo.getDesc());
            File file = new File(templateConfig.getTargetPath() + beanInfo.getClzz() + ".java");
            FileUtils.createFile(file);
            System.out.println();
            System.out.println(file.getPath());
            System.out.println();
            Writer out = new FileWriter(file);
//      Writer out = new OutputStreamWriter(System.out);  
            template.process(templateConfig, out);  
            out.flush();  
        } catch(Exception e) {
            LOGGER.error("generateJavaBean error," + e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }
    
}
