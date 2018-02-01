package org.bryadong.generator.model;

import java.util.List;

/**
 * TODO
 * 
 * @author dongdaiming
 */
public class BeanInfo {

    private String clzz;
    
    private String desc;

    private List<FieldInfo> fields;
    
    @Override
    public String toString() {
        return "BeanInfo [clzz=" + clzz + ", desc=" + desc + "]";
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
