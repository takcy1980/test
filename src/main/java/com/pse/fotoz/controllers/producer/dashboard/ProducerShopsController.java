package com.pse.fotoz.controllers.producer.dashboard;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.entities.Shop;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Robert
 */
@Controller
@RequestMapping("/producer/dashboard/shops")
public class ProducerShopsController {

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
            public String uri = "/producer/dashboard/shops";
            public String redirect = request.getRequestURL().toString();
        });
        mav.setViewName("producer/dashboard/shops.twig");

        return mav;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public ModelAndView provideNewShopForm(HttpServletRequest request) {
        ModelAndView mav = ModelAndViewBuilder.empty().
                    withProperties(request).
                    build();

        List<String> existingShopLogins = 
                HibernateEntityHelper.all(Shop.class).stream().
                map(s -> s.getLogin()).
                collect(Collectors.toList());
        
        mav.addObject("existing_shop_logins", existingShopLogins);
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/producer/dashboard/shops";
            public String redirect = request.getRequestURL().toString();
        });
        mav.setViewName("producer/dashboard/shops_new.twig");

        return mav;
    }
}
