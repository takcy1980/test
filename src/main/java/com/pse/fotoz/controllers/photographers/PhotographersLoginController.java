package com.pse.fotoz.controllers.photographers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/photographers/login")
public class PhotographersLoginController{
 
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView doGet(){ 
		return new ModelAndView("photographers/login/login.jsp");
	}
}