package com.pse.fotoz.controllers.producer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/producer/panel")
public class ProducerLoginController{
 
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView doGet(){ 
		return new ModelAndView("producer/login/panel.jsp");
	}
}