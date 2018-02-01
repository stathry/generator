package org.free.commons.generator.enums;

public enum DataTypeEnums {

    BYTE("Byte", "0"), SHORT("Short", "0"), INT("Integer", "0"),
    LONG("Long", "0"), FLOAT("Float", "0.0f"), DOUBLE("Double", "0.0d"), 
    BOOLEAN("Boolean", "false"), CHAR("Character", "'\u0000'"), STRING("String", "null"), 
    DATE("Date", "null");
    
    private String type;
    private String defaultValue;

    private DataTypeEnums(String type, String defaultValue) {
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public String type() {
        return type;
    }

    public String defaultValue() {
        return defaultValue;
    }
    
    public static String getTypeByName(String name) {
        if(name == null || name.length() == 0) {
            return name;
        }
        for(DataTypeEnums e : values()) {
            if(e.name().equalsIgnoreCase(name.trim())) {
                return e.type();
            }
        }
        return name.trim();
    }
    
}
