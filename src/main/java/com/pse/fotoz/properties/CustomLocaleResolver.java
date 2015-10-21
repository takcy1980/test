/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.properties;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author Gijs
 */
public class CustomLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        
        Locale loc; 
        
        try{
            loc = new Locale(request.getSession().getAttribute("lang").toString());
        } catch (NullPointerException ex){
            //language not implemented in visited page
            //default language NL
            loc = new Locale("nl");
        }

        return loc;
    }

    @Override
    public void setLocale(HttpServletRequest hsr, HttpServletResponse hsr1, Locale locale) {
        //locale not able to change via set
    }
}
