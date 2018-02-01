package org.bryadong.generator.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * TODO
 * 
 * @author dongdaiming
 */
@Component
public class JavaTemplateConfig {

    @Value("${template.dir}")
    private String templateDir;

    @Value("${template.templateName}")
    private String templateName;

    @Value("${template.targetPath}")
    private String targetPath;

    @Value("${template.copyright}")
    private String copyright;

    @Value("${template.author}")
    private String author;

    @Value("${template.pkg}")
    private String pkg;
    
    private String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");

    private String clzz;
    
    private String desc;
    
    private List<FieldInfo> fields;

    public String getTemplateDir() {
        return templateDir;
    }

    public void setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClzz() {
        return clzz;
    }

    public void setClzz(String clzz) {
        this.clzz = clzz;
    }

    public List<FieldInfo> getFields() {
        return fields;
    }

    public void setFields(List<FieldInfo> fields) {
        this.fields = fields;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
