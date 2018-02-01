package org.free.commons.generator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bryadong.generator.model.BeanInfo;
import org.bryadong.generator.model.FieldInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import freemarker.template.TemplateException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class SpringTest extends AbstractJUnit4SpringContextTests {
    
    @Test
    public void test() {
        System.out.println("success");
    }

    @Test
    public void test2() throws IOException {
        ClassPathResource res = new ClassPathResource("META-INF/spring-context.xml");
        System.out.println(res.getFile().exists());
    }
    

}
