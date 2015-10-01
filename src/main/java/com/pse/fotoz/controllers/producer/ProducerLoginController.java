package com.pse.fotoz.controllers.producer;

import com.pse.fotoz.properties.LocaleUtil;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value={"/producer/login/login", "/producer/login/panel"})
public class ProducerLoginController {
 
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView loadLoginScreen(HttpServletRequest request) {
            ModelAndView mav = new ModelAndView();           
            Map<String, String> labels;
            
            try {
                labels = LocaleUtil.getProperties(
                        request.getSession().getAttribute("lang").toString());
            } catch (IllegalArgumentException | NullPointerException e) {
                request.getSession().setAttribute("lang", "nl");
                labels = LocaleUtil.getProperties(
                        request.getSession().getAttribute("lang").toString());
            }
            
            mav.addObject("labels", labels);
            mav.addObject("page", new Object() {
                public String lang = request.getSession().
                        getAttribute("lang").toString();
                public String redirect = request.getRequestURL().toString();
            });            
           
            String viewName = request.getRequestURI();
            viewName = viewName.substring(4) + ".twig";
            mav.setViewName(viewName); 
            
            return mav;
	}
        
        /*
        @Issue 
        not yet implemented
         */
        @RequestMapping(method = RequestMethod.POST)
	public ModelAndView serviceLoginRequest() {
            ModelAndView mav = new ModelAndView();
            
            mav.setViewName("producer/login/panel.twig");            
            
            mav.addObject("labels", LocaleUtil.getProperties("en"));
            mav.addObject("page", new Object() {
                public String lang = "en";
            });
            mav.addObject("error", 
                    "The login functionality is not yet implemented.");
            
            return mav;
	}
}