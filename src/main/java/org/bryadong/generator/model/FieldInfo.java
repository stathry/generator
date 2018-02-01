package org.bryadong.generator.model;

/**
 * TODO
 * 
 * @author dongdaiming
 */
public class FieldInfo {

    private String name;

    private String type;

    private String comment;

    public FieldInfo() {
        super();
    }

    public FieldInfo(String name, String type, String comment) {
        super();
        this.name = name;
        this.type = type;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "FieldInfo [name=" + name + ", type=" + type + ", comment="
                + comment + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
