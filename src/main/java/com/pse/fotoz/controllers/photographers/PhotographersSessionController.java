package com.pse.fotoz.controllers.photographers;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.entities.*;
import com.pse.fotoz.helpers.encryption.PictureSessionCodeGen;
import com.pse.fotoz.helpers.forms.Parser;
import com.pse.fotoz.helpers.forms.PersistenceFacade;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import com.pse.fotoz.helpers.users.Users;
import com.pse.fotoz.properties.LocaleUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Gijs
 */
@Controller
@RequestMapping("/photographers/shop/sessions")
public class PhotographersSessionController {

    /**
     * Displays all picture sessions in a shop to a photographer
     *
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView displaySessions(HttpServletRequest request) {
        ModelAndView mav = ModelAndViewBuilder.empty().
                withProperties(request).
                build();

        //get current shops sessions and sort by id
        Shop shop = HibernateEntityHelper.find(Shop.class, "login",
                Users.currentUsername().get())
                .get(0);
        List<PictureSession> sessions = new ArrayList<>(shop.getSessions());
        Collections.sort(sessions);
        
        mav.addObject("sessions", sessions);
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/photographers/shop/sessions";
            public String redirect = request.getRequestURL().toString();
        });
        mav.setViewName("photographers/shop/sessions.twig");

        return mav;
    }

    /**
     * Displays a form to create a new picture session.
     *
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public ModelAndView provideNewProductForm(HttpServletRequest request) {
        ModelAndView mav = ModelAndViewBuilder.empty().
                withProperties(request).
                build();

        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/photographers/shop/sessions";
            public String redirect = request.getRequestURL().toString();
        });
        mav.setViewName("photographers/shop/sessions_new.twig");

        return mav;
    }

    /**
     * Handles a request to create a new Picture Session
     *
     * @param newPicSession Picture Session to be created
     * @param resultPicSession result of Validation of Picture Session
     * @param request http request
     * @return corresponding MAV depending on succes of creating Picture Session
     */
    @RequestMapping(method = RequestMethod.POST, value = "/new")
    @ResponseBody
    public ModelAndView handleSessionCreation(
            @ModelAttribute(value = "newPicSession")
            @Valid PictureSession newPicSession,
            BindingResult resultPicSession,
            HttpServletRequest request) {

        ModelAndView mav = ModelAndViewBuilder.empty().
                withProperties(request).
                build();
        mav.setViewName("photographers/shop/sessions_new.twig");

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
                Shop shop = HibernateEntityHelper.find(Shop.class, "login",
                        Users.currentUsername().get())
                        .get(0);

                //get code
                String code = PictureSessionCodeGen.sessionCode(
                        shop.getId(),
                        shop.getSessions().size());

                //persist new Picture Session
                PersistenceFacade.addPictureSession(shop, code, title,
                        description, isPublic);

                //@ToDo change succes viewname
                //no errors found.
                mav.setViewName("photographers/shop/sessions_new.twig");
            } catch (HibernateException ex) {
                Logger.getLogger(PhotographersSessionController.class.getName()).
                        log(Level.SEVERE, null, ex);
                errors.add(LocaleUtil.getProperties(request).
                        get("ERROR_INTERNALDATABASEERROR"));
            }
        }

        mav.addObject("errors", errors);
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/photographers/shop/sessions";
            public String redirect = request.getRequestURL().toString();
        });

        return mav;
    }
    
    /**
     * Displays a picture session.
     *
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/session")
    public ModelAndView showSession(HttpServletRequest request) {
        ModelAndView mav = ModelAndViewBuilder.empty().
                withProperties(request).
                build();
        
        //@Todo catch nosuchelement exception
        int id = Parser.parseInt(request.getParameter("id")).get();
        Set<Picture> pictures = HibernateEntityHelper
                .byId(PictureSession.class, id)
                .get()
                .getPictures();
        
        mav.addObject("pictures", pictures);
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/photographers/shop/sessions";
            public String redirect = request.getRequestURL().toString();
        });
        mav.setViewName("photographers/shop/session.twig");

        return mav;
    }

}
