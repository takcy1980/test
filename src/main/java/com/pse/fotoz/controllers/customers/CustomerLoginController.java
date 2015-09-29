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
		return new ModelAndView("customers/login/login.jsp");
	}
}