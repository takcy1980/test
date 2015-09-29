package com.pse.fotoz.controllers.common;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lang")
public class LanguageController{
 
	@RequestMapping(value = "/{lang}")
	public String setLanguage(@PathVariable("lang") String lang, 
                HttpServletRequest request) {
            request.getSession().setAttribute("lang", lang);
            
            return "redirect:" + request.getParameter("redirect");
	}
}