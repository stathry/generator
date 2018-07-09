/*
 * ${copyright}
 */
package ${pkg};  

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ${desc}
 * @author ${author}
 * @date ${date}
 */  
 
@Entity
@Table(name = "${table}")
public class ${clzz} implements Serializable {  

	private static final long serialVersionUID = 1L;
  
  <#list fields as field>  
    /** ${field.comment} */
    @Column(name="${field.collumn}")
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
        return "${clzz}[" +
            <#list fields as field>
                <#if field_index!=0> + ",${field.name} = " + ${field.name}<#else>"${field.name} = " + ${field.name}</#if>
            </#list>
        + "]";
    }
}  