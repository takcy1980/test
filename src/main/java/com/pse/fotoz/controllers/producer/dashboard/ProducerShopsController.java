package com.pse.fotoz.controllers.producer.dashboard;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.entities.Photographer;
import com.pse.fotoz.dbal.entities.Shop;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import com.pse.fotoz.properties.LocaleUtil;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public ModelAndView handleNewShopForm(@ModelAttribute(value="newShop") @Valid Shop newShop, BindingResult resultShop, 
                                          @ModelAttribute(value="newPhotographer") @Valid Photographer newPhotographer, BindingResult resultPhotographer,
                                          HttpServletRequest request) {
        
        ModelAndView mav = ModelAndViewBuilder.empty().build();
        Map<String, String> labels = LocaleUtil.getProperties(request);
        mav.addObject("labels", labels);
        
        List<String> errors = new ArrayList<>();
        
        if(resultShop.hasFieldErrors()){
            Iterator<FieldError> it = resultShop.getFieldErrors().iterator();
            while(it.hasNext()){
                FieldError err = it.next();
                errors.add(
                    err.getDefaultMessage()
                    //MessageFormat.format(
                    //    labels.get(err.getDefaultMessage()),
                    //    err.getField()
                    //)
                );
            }
        }
        
        //NOG OMZEttEN NAAR FIELDERRORS
        if(resultPhotographer.hasErrors()){
            Iterator<ObjectError> it = resultPhotographer.getAllErrors().iterator();
            while(it.hasNext()){
                errors.add(labels.get(it.next().getDefaultMessage()));
            }
        }
        
        mav.setViewName("producer/dashboard/shops_new.twig");
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
