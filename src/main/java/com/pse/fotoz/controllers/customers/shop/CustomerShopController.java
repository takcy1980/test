package com.pse.fotoz.controllers.customers.shop;

import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import com.pse.fotoz.properties.LocaleUtil;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("customers/shop")
public class CustomerShopController {
 
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadLoginScreen(HttpServletRequest request) {
        ModelAndView mav = ModelAndViewBuilder.empty().
                    withProperties(request).
                    build();

        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String redirect = request.getRequestURL().toString();
        });            

        mav.setViewName("customers/shop/index.twig");

        return mav;
    }

    /*
    @Issue 
    not yet implemented
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView serviceLoginRequest() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("customers/shop/index.twig");            

        mav.addObject("labels", LocaleUtil.getProperties("en"));
        mav.addObject("page", new Object() {
            public String lang = "en";
        });
        mav.addObject("error", 
                "The login functionality is not yet implemented.");

        return mav;
    }
}
