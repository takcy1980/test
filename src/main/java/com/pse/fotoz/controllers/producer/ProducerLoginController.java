package com.pse.fotoz.controllers.producer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/producer/panel")
public class ProducerLoginController{
 
	@RequestMapping(method = RequestMethod.GET)
<<<<<<< HEAD
	public ModelAndView loadLoginScreen() {
            ModelAndView mav = new ModelAndView();
            
            mav.setViewName("producer/login/login.twig");
            
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
            mav.addObject("page", new Object() {
                public String lang = "en";
            });
            mav.addObject("error", 
                    "The login functionality is not yet implemented.");
            
            return mav;
=======
	public ModelAndView doGet(){ 
		return new ModelAndView("producer/login/panel.jsp");
>>>>>>> e769753923cb1526900d8742305b0b8550de3df5
	}
}