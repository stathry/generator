# generator
##定制化代码生成工具<br/>
自动生成Mybatis ORM文件步骤:
1) 修改jdbc配置:/conf/jdbc.properties
2) 修改模板配置(非必须):/conf/template.properties,可指定项目顶级package，存放生成文件的顶级路径
3) 生成文件:<br/>
a.生成所有表对应的javaBean,mapper,DAO文件运行该方法:<br/>
org.stathry.generator.MyBatisGeneratorTest.testSmartGeneratorWithDAO<br/>
b.生成所有表对应的javaBean,mapper运行该方法:<br/>
org.stathry.generator.MyBatisGeneratorTest.testSmartGenerator<br/>

指定表自动生成Mybatis ORM文件运行该方法:<br/>
org.stathry.generator.MyBatisGeneratorTest.testSmartGenerateByTables
