package com.campusdual.cd2023bfs3g1.ws.core.rest;

import com.campusdual.cd2023bfs3g1.api.core.service.ISaleOrdersLService;
import com.ontimize.jee.server.rest.ORestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/saleordersl")
public class SaleOrdersLRestController extends ORestController<ISaleOrdersLService> {

    @Autowired
    private ISaleOrdersLService saleService;

    @Override
    public ISaleOrdersLService getService() {
        return this.saleService;
    }
}
