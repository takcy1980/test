/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.controllers.photographers.shop;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.entities.PictureSession;
import com.pse.fotoz.dbal.entities.Shop;
import com.pse.fotoz.helpers.forms.Parser;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import com.pse.fotoz.helpers.users.Users;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author 310054544
 */
@Controller
@RequestMapping("photographers/shop/{shop}/sessions/")
public class PhotographerSessionController {
    
    
    @RequestMapping(value = "/{session}", method = RequestMethod.GET)
    public ModelAndView displayPictureSessions(@PathVariable("shop") String shopid, @PathVariable("session") String sessionid,
            HttpServletRequest request) {
        ModelAndView mav = ModelAndViewBuilder.empty().
                withProperties(request).
                build();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username

        mav.addObject("username", name);

        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/customers/shops/";
            public String redirect = request.getRequestURL().toString();
        });

        mav.setViewName("photographers/shop/session.twig");

        Optional<Shop> shop = HibernateEntityHelper.find(Shop.class,
                "login", shopid).stream()
                .findAny();

        int sessionId = Parser.parseInt(sessionid).
                orElse(Integer.MIN_VALUE);

        final Integer userid = Users.currentUserAccount().
                map(a -> a.getId()).
                orElse(Integer.MIN_VALUE);

        Optional<PictureSession> session = shop.isPresent()
                ? shop.get().getSessions().stream().
                filter(s -> s.getId() == sessionId).
                findAny()
                : Optional.empty();

       
        
           if (shopid.equals(name)) {
            mav.addObject("shop", shop.get());
            mav.addObject("session", session.get());
        } else {
            mav.addObject("error",
                    "This is not allowed");
        }
         
            
            return mav;
        

    }
    
     @RequestMapping(value = "/{session}", method = RequestMethod.POST)
      public ResponseEntity UpdatePriceForm(HttpServletRequest request)
      {
          
          
        return null;
          
      }
    
}
