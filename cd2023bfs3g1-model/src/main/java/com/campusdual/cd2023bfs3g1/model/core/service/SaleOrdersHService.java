package com.campusdual.cd2023bfs3g1.model.core.service;

import com.campusdual.cd2023bfs3g1.api.core.service.ISaleOrdersHService;
import com.campusdual.cd2023bfs3g1.model.core.dao.SaleOrdersHDao;
import com.campusdual.cd2023bfs3g1.model.core.dao.SaleOrdersLDao;
import com.campusdual.cd2023bfs3g1.model.core.dao.ShoppingCartDao;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Lazy
@Service("SaleOrdersHService")
public class SaleOrdersHService implements ISaleOrdersHService {

    @Autowired
    private SaleOrdersHDao saleOrdersHDao;
    @Autowired
    private SaleOrdersLDao saleOrdersLDao;
    @Autowired
    private ShoppingCartDao shoppingCartDao;
    @Autowired
    private DefaultOntimizeDaoHelper daoHelper;


    //Sample to permission
    //@Secured({ PermissionsProviderSecured.SECURED })
    public EntityResult saleordershQuery(Map<String, Object> keyMap, List<String> attrList) {
        return this.daoHelper.query(saleOrdersHDao, keyMap, attrList);
    }

    public EntityResult saleordershInsert(Map<String, Object> attrMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //Recogemos el nombre de usuario
        attrMap.put(SaleOrdersHDao.ATTR_USER_, authentication.getName());
        attrMap.put(SaleOrdersHDao.ATTR_SALEDATE, new Date()); //Recogemos la fecha del pedido
        EntityResult resultInsert = this.daoHelper.insert(saleOrdersHDao, attrMap);
        if (resultInsert.isWrong()) { //En caso de que haya fallado la consulta devolvemos el error al front
            return resultInsert;
        }
        Integer saleId = (Integer) resultInsert.get(SaleOrdersHDao.ATTR_ID);// guardamos el id de la cabecera del pedido
        Date saleDate = (Date) attrMap.get(SaleOrdersHDao.ATTR_SALEDATE);
        List<String> fields = new ArrayList<>(); //campos por los cuales voy a consultar
        fields.add(ShoppingCartDao.ATTR_USER_);
        fields.add(ShoppingCartDao.ATTR_PRODUCT_ID);
        fields.add(ShoppingCartDao.ATTR_QTY);
        fields.add(ShoppingCartDao.ATTR_PRICE);
        fields.add(ShoppingCartDao.ATTR_TOTAL);
        Map<String, Object> filter = new HashMap<>(); //filtro que voy a realizar para la consulta para saber si ya existe en el carrito el producto
        filter.put(ShoppingCartDao.ATTR_USER_, authentication.getName());
        EntityResult shoppingCartLines = daoHelper.query(shoppingCartDao, filter, fields); // hago la consulta para ver si tengo ese producto y cuantos tengo
        if (shoppingCartLines.isWrong()) { //En caso de que haya fallado la consulta devolvemos el error al front
            return shoppingCartLines;
        }
        int i;
        for (i = 0; i < shoppingCartLines.calculateRecordNumber(); i++) {
            Integer id = (Integer) shoppingCartLines.getRecordValues(i).get(ShoppingCartDao.ATTR_QTY);
            Map<String, Object> updateKeys = new HashMap<>();
            updateKeys.put(SaleOrdersLDao.ATTR_PRODUCT_ID, shoppingCartLines.getRecordValues(i).get(ShoppingCartDao.ATTR_PRODUCT_ID));
            updateKeys.put(SaleOrdersLDao.ATTR_USER_, attrMap.put(SaleOrdersHDao.ATTR_USER_, authentication.getName()));
            updateKeys.put(SaleOrdersLDao.ATTR_QTY, shoppingCartLines.getRecordValues(i).get(ShoppingCartDao.ATTR_QTY));
            updateKeys.put(SaleOrdersLDao.ATTR_TOTAL, shoppingCartLines.getRecordValues(i).get(ShoppingCartDao.ATTR_TOTAL));
			updateKeys.put(SaleOrdersLDao.ATTR_SALEORDERSH_ID, saleId);
			updateKeys.put(SaleOrdersLDao.ATTR_PRICE, shoppingCartLines.getRecordValues(i).get(ShoppingCartDao.ATTR_PRICE));
			updateKeys.put(SaleOrdersLDao.ATTR_SALEDATE, saleDate);
			EntityResult result =this.daoHelper.insert(this.saleOrdersLDao,updateKeys);
        }
        //Map<String, Object> deleteFilter = new HashMap<>();
        //deleteFilter.put(ShoppingCartDao.ATTR_USER_, authentication.getName());
//        EntityResult deleteResult=this.daoHelper.delete(this.shoppingCartDao,deleteFilter);
//        if (resultInsert.isWrong()) { //En caso de que haya fallado la consulta devolvemos el error al front
//            return deleteResult;
//        }
        return resultInsert;
    }

    public EntityResult saleordershUpdate(Map<String, Object> attrMap, Map<String, Object> keyMap) {
        return this.daoHelper.update(saleOrdersHDao, attrMap, keyMap);
    }

    public EntityResult saleordershDelete(Map<String, Object> keyMap) {
        // Recogemos el id del pedido que nos piden borrar
        Integer id = Integer.parseInt(keyMap.get(SaleOrdersHDao.ATTR_ID).toString());
        // Tenemos que recoger las lineas de pedido para borrarlas antes que la cabecera
        Map<String, Object> filter = new HashMap<>();
        List<String> columns = new ArrayList<>();
        filter.put(SaleOrdersLDao.ATTR_SALEORDERSH_ID, id);
        columns.add(SaleOrdersLDao.ATTR_SALEORDERSL_ID);
        EntityResult resultLines = this.daoHelper.query(saleOrdersLDao, filter, columns);
        if (resultLines.isWrong()) { //En caso de que haya fallado la consulta devolvemos el error al front
            return resultLines;
        }
        // Como tenemos que borrar por id vamos linea a linea
        int i;
        for (i = 0; i < resultLines.calculateRecordNumber(); i++) {
            filter.clear();
            filter.put(SaleOrdersLDao.ATTR_ID, resultLines.getRecordValues(i).get(SaleOrdersLDao.ATTR_SALEORDERSL_ID));
            EntityResult result = this.daoHelper.delete(this.saleOrdersLDao, filter);
            if (result.isWrong()) { //En caso de que haya fallado la consulta devolvemos el error al front
                return result;
            }
        }
        // Borro la cabecera
        filter.clear();
        filter.put(SaleOrdersLDao.ATTR_ID, id);
        EntityResult resultHeader = this.daoHelper.delete(this.saleOrdersHDao, filter);
        if (resultHeader.isWrong()) { //En caso de que haya fallado la consulta devolvemos el error al front
            return resultHeader;
        }
        return resultHeader;
    }

    @Override
    public EntityResult saleordershtotalQuery(Map<String, Object> keyMap, List<String> attrList) {
        return this.daoHelper.query(saleOrdersHDao, keyMap, attrList, SaleOrdersHDao.QUERY_VTOTAL);
    }

    @Override
    public EntityResult saleordershrelated_dataQuery(Map<String, Object> keyMap, List<String> attrList) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> userKeyMap = new HashMap<>((Map<String, Object>) keyMap);
        userKeyMap.put(SaleOrdersHDao.ATTR_SALEORDERSH_USER_,authentication.getName());
        return this.daoHelper.query(saleOrdersHDao, userKeyMap, attrList, SaleOrdersHDao.QUERY_VRELATED_DATA);
    }

}
