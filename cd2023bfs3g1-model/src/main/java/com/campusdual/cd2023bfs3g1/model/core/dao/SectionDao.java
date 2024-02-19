package com.campusdual.cd2023bfs3g1.model.core.dao;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository(value="SectionDao")
@Lazy
@ConfigurationFile(
        configurationFile = "dao/SectionDao.xml",
        configurationFilePlaceholder = "dao/placeholders.properties")

public class SectionDao extends OntimizeJdbcDaoSupport {
    public static final String ATTR_ID = "id";
    public static final String ATTR_ES_NAME = "es_name";
    public static final String ATTR_EN_NAME = "en_name";
    public static final String ATTR_IMAGE = "image";

}
