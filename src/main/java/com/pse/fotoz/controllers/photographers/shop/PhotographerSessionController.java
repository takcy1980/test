/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.controllers.photographers.shop;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.entities.Picture;
import com.pse.fotoz.dbal.entities.PictureSession;
import com.pse.fotoz.dbal.entities.ProductType;
import com.pse.fotoz.dbal.entities.Shop;
import com.pse.fotoz.helpers.forms.MultipartFileValidator;
import com.pse.fotoz.helpers.forms.Parser;
import com.pse.fotoz.helpers.forms.PersistenceFacade;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import com.pse.fotoz.helpers.users.Users;
import com.pse.fotoz.properties.LocaleUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
            public String uri = "/photographers/shops/";
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
    @ResponseBody
    public ModelAndView UpdatePriceForm(@PathVariable("shop") String shopid, @PathVariable("session") String sessionid,
            HttpServletRequest request) {
        ModelAndView mav = ModelAndViewBuilder.empty().
                withProperties(request).
                build();

        List<String> errors = new ArrayList<>();

        try {
            double price = Double.parseDouble(request.getParameter("price"));
            int pictureId = Integer.parseInt(request.getParameter("picture_id"));

            PersistenceFacade.changePicturePrice(pictureId, BigDecimal.valueOf(price));

        } catch (HibernateException ex) {
            errors.add(ex.toString());
        } catch (NumberFormatException ex) {
            errors.add(LocaleUtil.getErrorProperties(request).
                        get("error_dot_comma"));
        } catch (javax.validation.ConstraintViolationException ex) {
            //errors.add("negative not allowed");
            errors.add(LocaleUtil.getErrorProperties(request).
                        get("error_decimal_price"));
           
        }

        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();

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

        mav.addObject("errors", errors);

        mav.addObject("shop", shop.get());
        mav.addObject("session", session.get());

        return mav;

    }

//     @RequestMapping(value = "/{session}", method = RequestMethod.POST)
//      public ResponseEntity UpdatePriceForm(HttpServletRequest request)
//      {
//          double price = Double.parseDouble(request.getParameter("price")) ;
//          int pictureId = Integer.parseInt(request.getParameter("picture_id"));
//          
//          
//        try {
//            PersistenceFacade.changePicturePrice(pictureId, BigDecimal.valueOf(price));
//        } catch (HibernateException | IllegalArgumentException ex) {
//            Logger.getLogger(PhotographerSessionController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return new ResponseEntity(HttpStatus.OK);
//        
//       
//          
//      }
}
