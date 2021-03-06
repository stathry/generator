/*
 * ${copyright}
 */
package ${pkg}.model;

import java.io.Serializable;
import java.util.Date;

/**
 * ${desc}
 * @author ${author}
 * @date ${generateTime}
 */  
 
public class ${clzz} {

  <#list fields as field>
    /** ${field.comment} */
    private ${field.type} ${field.name};
      
  </#list>  
  <#list fields as field>
    public ${field.type} get${field.name?cap_first}(){  
      return ${field.name};  
    }  
    
    public void set${field.name?cap_first}(${field.type} ${field.name}){  
      this.${field.name} = ${field.name};  
    } 
     
  </#list>
    @Override
    public String toString() {
        return "${clzz}[" + <#list fields as field> <#if field_index!=0> + ",${field.name} = " + ${field.name}<#else>"${field.name} = " + ${field.name}</#if>
        </#list> + "]";
    }
}  