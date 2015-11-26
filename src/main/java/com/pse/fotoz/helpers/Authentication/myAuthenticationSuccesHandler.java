/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.helpers.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 *
 * @author Gijs
 */
public class myAuthenticationSuccesHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    
    @Override
    protected String determineTargetUrl(HttpServletRequest request,
                                            HttpServletResponse response) {
         
        System.out.println(request.getParameter("redirect"));
        System.out.println("path:"+ request.getContextPath());
        System.out.println("url: " + request.getRequestURI());

        return "/app/test";
                
    }

}
