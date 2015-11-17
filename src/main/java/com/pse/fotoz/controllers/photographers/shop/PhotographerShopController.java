package com.pse.fotoz.controllers.photographers.shop;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.entities.PictureSession;
import com.pse.fotoz.dbal.entities.ProductType;
import com.pse.fotoz.dbal.entities.Shop;
import com.pse.fotoz.helpers.forms.Parser;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import com.pse.fotoz.helpers.users.Users;
import com.pse.fotoz.properties.LocaleUtil;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import static javafx.scene.input.KeyCode.T;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("photographers/shop")
public class PhotographerShopController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadLoginScreen(HttpServletRequest request, String shopid) {
        ModelAndView mav = ModelAndViewBuilder.empty().
                withProperties(request).
                build();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username

        shopid = name;

        mav.addObject("username", name);
        mav.addObject("page", new Object() {

            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String redirect = request.getRequestURL().toString();
        });

        Optional<Shop> shop = HibernateEntityHelper.find(Shop.class,
                "login", shopid).stream()
                .findAny();

        mav.addObject("shop", shop.get());

        mav.setViewName("photographers/shop/index.twig");

        return mav;
    }

    @RequestMapping(value = "/{shop}", method = RequestMethod.GET)
    public ModelAndView displayShopDetail(@PathVariable("shop") String shopid,
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

            public String redirect = request.getRequestURL().toString();
        });

        mav.setViewName("photographers/shop/sessions.twig");

        Optional<Shop> shop = HibernateEntityHelper.find(Shop.class,
                "login", shopid).stream()
                .findAny();

        if (shopid.equals(name)) {
            mav.addObject("shop", shop.get());
        } else {
            mav.addObject("error",
                    "This is not allowed");
        }

        return mav;

    }

    

    /*
     @Issue 
     not yet implemented
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView serviceLoginRequest() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("photographers/shop/index.twig");

        mav.addObject("labels", LocaleUtil.getProperties("en"));
        mav.addObject("page", new Object() {
            public String lang = "en";
        });
        mav.addObject("error",
                "The login functionality is not yet implemented.");

        return mav;
    }
}
