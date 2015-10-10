package com.pse.fotoz.controllers.customers.shops;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.entities.Shop;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/customers/shops")
public class CustomerShopsController {
 
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView displayShops(HttpServletRequest request) {
        ModelAndView mav = ModelAndViewBuilder.empty().
                    withProperties(request).
                    build();
        
        //find all shops that have at least one public session, of which there
        //is at least one non-hidden picture.
        List<Shop> shops = HibernateEntityHelper.all(Shop.class).stream().
                filter(shop -> shop.getSessions().stream().
                        anyMatch(session -> 
                                session.isPublic() && 
                                        session.getPictures().stream().
                                                anyMatch(picture -> 
                                                        !picture.isHidden()))).
                collect(toList());
        
        
        mav.addObject("shops", shops);
        
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/customers/shops";
            public String redirect = request.getRequestURL().toString();
        });            

        mav.setViewName("customers/shops.twig");

        return mav;
    }
}
