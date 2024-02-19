package com.campusdual.cd2023bfs3g1.model.core.dao;


import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;


@Lazy
@Repository(value = "UserDao")
@ConfigurationFile(
	configurationFile = "dao/UserDao.xml",
	configurationFilePlaceholder = "dao/placeholders.properties")
public class UserDao extends OntimizeJdbcDaoSupport {

    public static final String ID = "USER_";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String NAME = "NAME";
    public static final String SURNAME = "SURNAME";
    public static final String NIF = "NIF";
    public static final String USERBLOCKED = "USERBLOCKED";
    public static final String LASTPASSWORDUPDATE = "LASTPASSWORDUPDATE";
    public static final String FIRSTLOGIN = "FIRSTLOGIN";

}
