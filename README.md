# generator
##定制化代码生成工具<br/>
自动生成Mybatis ORM文件步骤:
1) 修改jdbc配置:/conf/jdbc.properties
2) 修改模板配置(非必须):/conf/template.properties
3) 一键运行该方法即可生成所有表对应的javaBean,mapper:<br/>
org.stathry.generator.MyBatisGeneratorTest.testSmartGenerator
<br/>

指定表自动生成Mybatis ORM文件:<br/>
org.stathry.generator.MyBatisGeneratorTest.testSmartGenerateByTables
