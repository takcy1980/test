package com.pse.fotoz.controllers.producer.dashboard;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.entities.Shop;
import com.pse.fotoz.helpers.forms.InputValidator;
import com.pse.fotoz.helpers.forms.InputValidator.ValidationResult;
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
 * Controller handling the display and addition of shops for the producer.
 * @author Robert
 */
@Controller
@RequestMapping("/producer/dashboard/shops")
public class ProducerShopsController {
    
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
            public String uri = "/producer/dashboard/shops";
            public String redirect = request.getRequestURL().toString();
        });
        mav.setViewName("producer/dashboard/shops.twig");

        return mav;
    }
    
    /**
     * Displays a form to add new shops to the system to the producer.
     * @param request
     * @return 
     */
    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public ModelAndView provideNewShopForm(HttpServletRequest request) {
        ModelAndView mav = ModelAndViewBuilder.empty().
                    withProperties(request).
                    build();
        
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/producer/dashboard/shops";
            public String redirect = request.getRequestURL().toString();
        });
        mav.setViewName("producer/dashboard/shops_new.twig");

        return mav;
    }
    
    /**
     * Handles a request to add a new shop to the system by the producer.
     * @param request
     * @return 
     */
    @RequestMapping(method = RequestMethod.POST, value = "/new")
    public ModelAndView handleNewShopForm(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        
        List<String> errors = new ArrayList<>();
        
        ModelAndView mav = ModelAndViewBuilder.empty().
                    withProperties(request).
                    build();
        
        try {
            ValidationResult result = PersistenceFacade.addShop(login, password, 
                    name, address, city, email, phone, 
                    LocaleUtil.getProperties(request));
            
            if (result.status() == InputValidator.ValidationStatus.OK) {
                mav.setViewName("producer/dashboard/shops_new_success.twig");
            } else {
                errors.addAll(result.errors());
                mav.setViewName("producer/dashboard/shops_new.twig");
            }
        } catch (HibernateException ex) {
            Logger.getLogger(ProducerShopsController.class.getName()).
                    log(Level.SEVERE, null, ex);
            errors.add(LocaleUtil.getProperties(request).
                    get("ERROR_INTERNALDATABASEERROR"));
            
            mav.setViewName("producer/dashboard/shops_new.twig");
        }
        
        
        mav.addObject("errors", errors);
        
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/producer/dashboard/shops";
            public String redirect = request.getRequestURL().toString();
        });
        

        return mav;
    }
}
