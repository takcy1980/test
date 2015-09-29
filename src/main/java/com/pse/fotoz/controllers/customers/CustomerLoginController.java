package com.pse.fotoz.controllers.customers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/customers/login")
public class CustomerLoginController{
 
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView doGet(){ 
		 ModelAndView mav = new ModelAndView();
            
            mav.setViewName("customers/login/login.twig");
            
            mav.addObject("page", new Object() {
                public String lang = "en";
            });
            
            return mav;
	}
}
