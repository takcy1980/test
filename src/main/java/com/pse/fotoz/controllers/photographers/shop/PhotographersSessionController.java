package com.pse.fotoz.controllers.photographers.shop;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.entities.*;
import com.pse.fotoz.helpers.Authentication.OwnershipHelper;
import com.pse.fotoz.helpers.encryption.PictureSessionCodeGen;
import com.pse.fotoz.helpers.forms.Parser;
import com.pse.fotoz.helpers.forms.PersistenceFacade;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import com.pse.fotoz.helpers.users.Users;
import com.pse.fotoz.properties.LocaleUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Gijs
 */
@Controller
@RequestMapping("/photographers/shop/{shopId}/sessions")
public class PhotographersSessionController {

    /**
     * Displays all picture sessions in a shop to a photographer
     *
     * @param shopId id of shop
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView displaySessions(
            @PathVariable("shopId") String shopId,
            HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mav = ModelAndViewBuilder.empty().
                withProperties(request).
                withCookies(request,response).
                build();
        
        mav.addObject("shopId", shopId);
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/photographers/shop/sessions";
            public String redirect = request.getRequestURL().toString();
        });
        mav.setViewName("photographers/shop/sessions.twig");

        //get current shops sessions
        //redirects to public homepage in case of wrong user/shop combination
        try {
            Integer id = Parser.parseInt(shopId).orElse(null);
            Shop shop = Shop.getShopByID(id);

            if (OwnershipHelper.doesUserOwnShop(shop,
                    Users.currentUsername().orElse(null))) {
                List<PictureSession> sessions = shop.getSessions();
                mav.addObject("sessions", sessions);
            } else {
                mav = new ModelAndView("redirect:/login");
            }
        } catch (NullPointerException ex) {
            //NullPointerException: wrong url
            mav = new ModelAndView("redirect:/app/");
        }

        return mav;
    }

    /**
     * Displays a form to create a new picture session.
     *
     * @param shopId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public ModelAndView provideNewProductForm(
            @PathVariable("shopId") String shopId,
            HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mav = ModelAndViewBuilder.empty().
                withProperties(request).
                withCookies(request,response).
                build();

        mav.addObject("shopId", shopId);
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/photographers/shop/sessions";
            public String redirect = request.getRequestURL().toString();
        });
        mav.setViewName("photographers/shop/sessions_new.twig");

        //check ownership and redirect in case of wrong user/shop combination
        try {
            Integer id = Parser.parseInt(shopId).orElse(null);
            Shop shop = Shop.getShopByID(id);
            if (!(OwnershipHelper.doesUserOwnShop(shop,
                    Users.currentUsername().orElse(null)))) {
                mav = new ModelAndView("redirect:/login");
            }
        } catch (NullPointerException ex) {
            //NullPointerException: wrong url
            mav = new ModelAndView("redirect:/app/");
        }

        return mav;
    }

    /**
     * Handles a request to create a new Picture Session
     *
     * @param newPicSession Picture Session to be created
     * @param resultPicSession result of Validation of Picture Session
     * @param shopId shops id
     * @param request http request
     * @param redirectAttributes attributes to be added to a redirect view
     * @return corresponding MAV depending on succes of creating Picture Session
     */
    @RequestMapping(method = RequestMethod.POST, value = "/new")
    @ResponseBody
    public ModelAndView handleSessionCreation(
            @ModelAttribute(value = "newPicSession")
            @Valid PictureSession newPicSession,
            BindingResult resultPicSession,
            @PathVariable("shopId") String shopId,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        boolean sessionCreated = false;
        List<String> errors = new ArrayList<>();

        //check validation errors
        for (FieldError e : resultPicSession.getFieldErrors()) {
            errors.add(e.getDefaultMessage());
        }

        if (errors.isEmpty()) {
            try {
                //get form input
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                boolean isPublic = Boolean.parseBoolean(request.getParameter("isPublic"));

                //get shop
                Integer id = Parser.parseInt(shopId).orElse(null);
                Shop shop = Shop.getShopByID(id);

                //check ownership
                if (OwnershipHelper.doesUserOwnShop(shop,
                        Users.currentUsername().orElse(null))) {
                    //get new session code
                    String code = PictureSessionCodeGen.sessionCode(
                            shop.getId(),
                            shop.getSessions().size());

                    //persist new Picture Session
                    PersistenceFacade.addPictureSession(shop, code, title,
                            description, isPublic);

                    sessionCreated = true;
                }

            } catch (HibernateException ex) {
                Logger.getLogger(PhotographersSessionController.class.getName()).
                        log(Level.SEVERE, null, ex);
                errors.add(LocaleUtil.getProperties(request).
                        get("ERROR_INTERNALDATABASEERROR"));
            } catch (NullPointerException ex) {
                //non existing shop or user
                //no action needed. redirect will take place.
            }
        }

        ModelAndView mav;
        if (sessionCreated) {
            mav = new ModelAndView("redirect:/app/photographers/shop/" + shopId + "/sessions/");
        } else {
            //in case of wrong ownership page will be redirected once more to /app/
            redirectAttributes.addFlashAttribute("errors", errors);
            mav = new ModelAndView("redirect:/app/photographers/shop/" + shopId + "/sessions/new");
        }
        return mav;
    }

    /**
     * Displays a picture session.
     *
     * @param shopId
     * @param sessionId id of picture session to be shown
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/{session}", method = RequestMethod.GET)
    public ModelAndView showPictureSession(
            @PathVariable("shopId") String shopId,
            @PathVariable("session") String sessionId,
            HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mav = ModelAndViewBuilder.empty().
                withProperties(request).
                withCookies(request,response).
                build();

        mav.addObject("shopId", shopId);
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/photographers/shop/sessions";
            public String redirect = request.getRequestURL().toString();
        });
        mav.setViewName("photographers/shop/session.twig");

        try {
            Integer id = Parser.parseInt(sessionId).orElse(null);

            PictureSession session = HibernateEntityHelper.byId(
                    PictureSession.class, id).orElse(null);

            //check ownership
            if (OwnershipHelper.doesUserOwnShop(session.getShop(),
                    Users.currentUsername().orElse(null))) {

                //TODO add pictures and stuff
                
                mav.addObject("session", session);
                
            } else {
                mav = new ModelAndView("redirect:/login");
            }

        } catch (NullPointerException ex) {
            //non existing shop or user
            mav = new ModelAndView("redirect:/app/");
        }

        return mav;
    }

}
