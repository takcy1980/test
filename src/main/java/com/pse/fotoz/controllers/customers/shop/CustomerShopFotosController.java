/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.controllers.customers.shop;

import com.pse.fotoz.controllers.producer.dashboard.ProducerShopsController;
import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.entities.Shop;
import com.pse.fotoz.helpers.forms.InputValidator;
import com.pse.fotoz.helpers.forms.PersistenceFacade;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import com.pse.fotoz.properties.LocaleUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author 310054544
 */
@Controller
@RequestMapping("/customers/shop/fotos")
public class CustomerShopFotosController {
    
    /**
     * Displays all active shops to the producer.
     * @param request
     * @return 
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView displayShops(HttpServletRequest request) {
        ModelAndView mav = ModelAndViewBuilder.empty().
                    withProperties(request).
                    build();

        List<Shop> shops = HibernateEntityHelper.all(Shop.class);
        
        mav.addObject("shops", shops);
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/customers/shop/fotos";
            public String redirect = request.getRequestURL().toString();
        });
        mav.setViewName("customers/shop/fotos.twig");

        return mav;
    }
    
    
    
   
    
}
