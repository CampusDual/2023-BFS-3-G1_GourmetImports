package com.campusdual.cd2023bfs3g1.model.core.dao;

import com.ontimize.jee.server.dao.common.ConfigurationFile;
import com.ontimize.jee.server.dao.jdbc.OntimizeJdbcDaoSupport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository(value="SaleOrdersLDao")
@Lazy
@ConfigurationFile(
        configurationFile = "dao/SaleOrdersLDao.xml",
        configurationFilePlaceholder = "dao/placeholders.properties")

public class SaleOrdersLDao extends OntimizeJdbcDaoSupport {
    public static final String ATTR_ID = "id";
    public static final String ATTR_PRICE = "price";
    public static final String ATTR_PRODUCT_ID = "product_id";
    public static final String ATTR_USER_ = "user_";
    public static final String ATTR_SALEDATE = "saledate";
    public static final String ATTR_QTY = "qty";
    public static final String ATTR_TOTAL = "total";
    public static final String ATTR_SALEORDERSH_ID = "saleordersh_id";
    public static final String ATTR_SALEORDERSL_ID = "saleordersl_id";
}
