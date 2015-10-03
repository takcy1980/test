package com.pse.fotoz.controllers.customers;

import com.pse.fotoz.properties.LocaleUtil;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/customers/login")
public class CustomerLoginController{
 
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
            
            mav.setViewName("customers/login/login.twig"); 
            
            return mav;
	}
        
        /*
        @Issue 
        not yet implemented
         */
//        @RequestMapping(method = RequestMethod.POST)
//	public ModelAndView serviceLoginRequest() {
//            ModelAndView mav = new ModelAndView();
//            
//            mav.setViewName("customers/login/registration.twig");            
//            
//            mav.addObject("labels", LocaleUtil.getProperties("en"));
//            mav.addObject("page", new Object() {
//                public String lang = "en";
//            });
////            mav.addObject("error", 
////                    "customers/login/registration.twig");
//            
//            return mav;
//	}
        
        
        @RequestMapping(method = RequestMethod.POST)
    public ModelAndView serviceLoginRequest(HttpServletRequest request,HttpServletResponse res) {
        ModelAndView mav = new ModelAndView();

        String name = request.getParameter("login_name");
        String password = request.getParameter("password");

       // mav.setViewName("producer/login/login.twig");

        mav.addObject("labels", LocaleUtil.getProperties("en"));
        mav.addObject("page", new Object() {
            public String lang = "en";
        });

        if (password.equals("admin")) {
            
            mav.setViewName("customers/login/loginSession.twig");
        } else {
            mav.setViewName("common/error/505.jsp");
        }

       return mav;
    }
}
