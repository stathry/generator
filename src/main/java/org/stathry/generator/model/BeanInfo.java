package org.stathry.generator.model;

import java.util.List;

/**
 * TODO
 * 
 * @author dongdaiming
 */
public class BeanInfo {

	private String clzz;

	private String className;

	private String table;

	private String desc;

	private List<FieldInfo> fields;
	private List<FieldInfo> insertFields;

    private String idType;
    private String idJdbcType;

    @Override
    public String toString() {
        return "BeanInfo{" +
                "clzz='" + clzz + '\'' +
                ", className='" + className + '\'' +
                ", table='" + table + '\'' +
                ", desc='" + desc + '\'' +
                ", fields=" + fields +
                ", idType='" + idType + '\'' +
                ", idJdbcType='" + idJdbcType + '\'' +
                '}';
    }

    public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdJdbcType() {
        return idJdbcType;
    }

    public void setIdJdbcType(String idJdbcType) {
        this.idJdbcType = idJdbcType;
    }

    public List<FieldInfo> getInsertFields() {
        return insertFields;
    }

    public void setInsertFields(List<FieldInfo> insertFields) {
        this.insertFields = insertFields;
    }
}
