package org.stathry.generator.enums;

public enum DBDataTypeEnums {

    BIT("Boolean"),
    BOOLEAN("Boolean"),
    INTEGER("Integer"),
    INT("Integer"),
    TINYINT("Integer"),
    SMALLINT("Integer"),
    MEDIUMINT("Integer"),
    BIGINT("Long"),
    ID("Long"),
    DECIMAL("Double"),
    FLOAT("Float"),
    DOUBLE("Double"),
    BLOB("byte[]"),
    VARCHAR("String"),
    CHAR("String"),
    TEXT("String"),
    TIMESTAMP("Date"),
    DATE("Date"),
    DATETIME("Date"),
    YEAR("Date"),
    ;
    
    private String type;

    private DBDataTypeEnums(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }

    public static String getTypeByName(String name) {
        if(name == null || name.length() == 0) {
            return name;
        }
        for(DBDataTypeEnums e : values()) {
            if(e.name().equalsIgnoreCase(name.trim())) {
                return e.type();
            }
        }
        return name;
    }

    public static String getNameByType(String type) {
        if(type == null || type.length() == 0) {
            return type;
        }
        String t = type.trim().toLowerCase();
        String et;
        for(DBDataTypeEnums e : values()) {
            et = e.type().toLowerCase();
            if(et.equals(t) || et.startsWith(t)) {
                return e.name();
            }
        }
        return type;
    }
    
}
