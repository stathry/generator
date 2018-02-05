package org.stathry.generator.enums;

public enum DBDataTypeEnums {

    VARCHAR("String"),
    CHAR("String"),
    BLOB("byte[]"),
    TEXT("String"),
    INTEGER("Long"),
    INT("Integer"),
    TINYINT("Integer"),
    SMALLINT("Integer"),
    MEDIUMINT("Integer"),
    BIT("Boolean"),
    BIGINT("Long"),
    FLOAT("Float"),
    DOUBLE("Double"),
    DECIMAL("Double"),
    BOOLEAN("Boolean"),
    ID("Long"),
    DATE("Date"),
    DATETIME("Date"),
    TIMESTAMP("Date"),
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
        return name.trim();
    }
    
}
