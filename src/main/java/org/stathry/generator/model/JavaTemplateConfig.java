package org.stathry.generator.model;

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

    @Value("${template.jpaTemplateName}")
    private String jpaTemplateName;

    @Value("${template.mybatisTemplateName}")
    private String mybatisTemplateName;

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
    
    private String table;
    
    private String desc;
    
    private List<FieldInfo> fields;

    @Override
    public String toString() {
        return "JavaTemplateConfig{" +
                "templateDir='" + templateDir + '\'' +
                ", jpaTemplateName='" + jpaTemplateName + '\'' +
                ", mybatisTemplateName='" + mybatisTemplateName + '\'' +
                ", targetPath='" + targetPath + '\'' +
                ", copyright='" + copyright + '\'' +
                ", author='" + author + '\'' +
                ", pkg='" + pkg + '\'' +
                ", date='" + date + '\'' +
                ", clzz='" + clzz + '\'' +
                ", table='" + table + '\'' +
                ", desc='" + desc + '\'' +
                ", fields=" + fields +
                '}';
    }

    public String getTemplateDir() {
        return templateDir;
    }

    public void setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
    }

    public String getJpaTemplateName() {
        return jpaTemplateName;
    }

    public void setJpaTemplateName(String jpaTemplateName) {
        this.jpaTemplateName = jpaTemplateName;
    }

    public String getMybatisTemplateName() {
        return mybatisTemplateName;
    }

    public void setMybatisTemplateName(String mybatisTemplateName) {
        this.mybatisTemplateName = mybatisTemplateName;
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

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

}
