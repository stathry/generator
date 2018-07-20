package org.stathry.generator;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.stathry.generator.enums.DAOType;
import org.stathry.generator.enums.DataTypeEnums;
import org.stathry.generator.model.BeanInfo;
import org.stathry.generator.model.ORMTemplateContext;
import org.stathry.generator.util.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Date;

/**
 * DAO文件生成
 * Created by dongdaiming on 2018-07-10 09:54
 */
@Component
public class DAOGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DAOGenerator.class);

    @Autowired
    private ORMTemplateContext templateContext;

    @Value("${jdbc.schema}")
    private String schema;
    private String genDAOTempName = "GenericDAO.ftl";
    private String genDAOImplTempName = "GenericDAOImpl.ftl";
    private String daoTempName = "mybatisDAOTemplate.ftl";
    private String daoImplTempName = "mybatisDAOImplTemplate.ftl";
    @Value("${orm.template.timePattern}")
    private String timePattern;

    public void generateDAOByTemplate(BeanInfo beanInfo, DAOType type) throws Exception {
        String tempName = typeToTempName(type);
        ORMTemplateContext tc = templateContext;
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new ClassPathResource(tc.getTemplateDir()).getFile());
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
        Template template = cfg.getTemplate(tempName);

        if(type == DAOType.DAO || type == DAOType.DAO_IMPL) {
            tc.setTable(beanInfo.getTable());
            tc.setFields(beanInfo.getFields());
            tc.setClzz(beanInfo.getClzz());
            tc.setDesc(beanInfo.getDesc());
            tc.setIdJdbcType(beanInfo.getIdJdbcType());
            tc.setIdType(DataTypeEnums.getTypeByName(beanInfo.getIdType()));
            tc.setInsertFields(beanInfo.getInsertFields());
        }
        tc.setGenerateTime(DateFormatUtils.format(new Date(), timePattern));

        Writer out = null;
        try {
            File file = new File(tc.getTargetPath() + schema + "/dao/" + typeToDAOName(type, beanInfo == null ? "" : beanInfo.getClzz()) + ".java");
            FileUtils.createFile(file);
            LOGGER.info("{} file has been generated, path is \"{}\".", type.name(), file.getAbsolutePath());
            out = new FileWriter(file);
            template.process(tc, out);
            out.flush();
        } catch(Exception e) {
            LOGGER.error("generate dao error," + e.getMessage(), e);
        } finally {
            if(out != null) {
                out.close();
            }
        }
    }

    private String typeToTempName(DAOType type) {
        if(type == null) {
            throw new IllegalArgumentException("illegal daoType.");
        }
        String tempName;
        switch (type) {
            case GEN_DAO: tempName = genDAOTempName; break;
            case GEN_DAO_IMPL: tempName = genDAOImplTempName; break;
            case DAO: tempName = daoTempName; break;
            case DAO_IMPL: tempName = daoImplTempName; break;
            default: throw new IllegalArgumentException("illegal daoType.");
        }
        return tempName;
    }

    private String typeToDAOName(DAOType type, String basicName) {
        if(type == null) {
            throw new IllegalArgumentException("illegal daoType.");
        }
        String daoName;
        switch (type) {
            case GEN_DAO: daoName = "GenericDAO"; break;
            case GEN_DAO_IMPL: daoName = "impl/GenericDAOImpl"; break;
            case DAO: daoName = basicName + "DAO"; break;
            case DAO_IMPL: daoName = "impl/" + basicName + "DAOImpl"; break;
            default: throw new IllegalArgumentException("illegal daoType.");
        }
        return daoName;
    }

}
