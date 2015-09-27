package com.pse.fotoz.controllers.producer;

import com.pse.fotoz.properties.LocaleUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/producer/login")
public class ProducerLoginController{
 
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView loadLoginScreen() {
            ModelAndView mav = new ModelAndView();
            
            mav.setViewName("producer/login/login.twig");
            mav.addObject("labels", LocaleUtil.getProperties("en"));
            mav.addObject("page", new Object() {
                public String lang = "en";
            });
            
            return mav;
	}
        
        /*
        @Issue 
        not yet implemented
         */
        @RequestMapping(method = RequestMethod.POST)
	public ModelAndView serviceLoginRequest() {
            ModelAndView mav = new ModelAndView();
            
            mav.setViewName("producer/login/login.twig");            
            
            mav.addObject("labels", LocaleUtil.getProperties("en"));
            mav.addObject("page", new Object() {
                public String lang = "en";
            });
            mav.addObject("error", 
                    "The login functionality is not yet implemented.");
            
            return mav;
	}
}