package com.campusdual.cd2023bfs3g1.api.core.service;


import com.ontimize.jee.common.dto.EntityResult;

import java.util.List;
import java.util.Map;


public interface ISaleOrdersHService {

    public EntityResult saleordershQuery(Map<String, Object> keyMap, List<String> attrList);
    public EntityResult saleordershInsert(Map<String, Object> attrMap);
    public EntityResult saleordershUpdate(Map<String, Object> attrMap, Map<String, Object> keyMap);
    public EntityResult saleordershDelete(Map<String, Object> keyMap);
    public EntityResult saleordershtotalQuery(Map<String, Object> keyMap, List<String> attrList);
    public EntityResult saleordershrelated_dataQuery(Map<String, Object> keyMap, List<String> attrList);

}