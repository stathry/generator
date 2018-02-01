/*
 * ${copyright}
 */
package ${pkg};  

/**
 * ${desc}
 * @author ${author}
 * @date ${date}
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
}  