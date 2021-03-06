package com.pse.fotoz.controllers.customers.shops;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.entities.Picture;
import com.pse.fotoz.dbal.entities.PictureSession;
import com.pse.fotoz.dbal.entities.Shop;
import com.pse.fotoz.dbal.entities.filters.PictureFilters;
import com.pse.fotoz.helpers.forms.Parser;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import com.pse.fotoz.helpers.users.Users;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller displaying picture sessions.
 * @author Robert
 */
@Controller
@RequestMapping("/customers/shops/{shop}/sessions/")
public class CustomerPictureSessionsController {
    
    /**
     * Displays the pictures of a given session.
     * @param shopname The login name of the associated shop.
     * @param sessionid The identity of the session.
     * @param request The associated request.
     * @param response The associated response.
     * @return View of "customers/shops/session.twig".
     */
    @RequestMapping(value = "/{session}", method = RequestMethod.GET)
    public ModelAndView displayPictureSessions(@PathVariable("shop") 
            String shopname, @PathVariable("session") String sessionid,
            HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = ModelAndViewBuilder.empty().                
                withProperties(request).
                withCookies(request, response).
                build();
        
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/customers/shops/" + sessionid + "/";
            public String redirect = request.getRequestURL().toString();
        });
        
        mav.setViewName("customers/shops/session.twig");
        
        Optional<Shop> shop = HibernateEntityHelper.find(Shop.class, 
                "login", shopname).stream()
                .findAny();
        
        int sessionId = Parser.parseInt(sessionid).
                orElse(Integer.MIN_VALUE);
        
        final Integer userid = Users.currentUserAccount().
                map(a -> a.getId()).
                orElse(Integer.MIN_VALUE);
        
        Optional<PictureSession> session = shop.
                flatMap(s -> s.getSessions().stream().
                        filter(s2 -> s2.getId() == sessionId).
                        filter(s2 -> s2.isPublic() ||
                                s2.getPermittedAccounts().stream().
                                        anyMatch(a -> a.getId() == userid)).
                        findAny());
        
        if (!session.isPresent()) {
            return new ModelAndView("redirect:/app/customers/shops/");
        } else {
            List<Picture> visiblePictures = session.get().
                    getPictures().stream().sorted().
                    filter(PictureFilters.isVisible()).
                    collect(toList());
            
            mav.addObject("shop", shop.get());
            mav.addObject("session", session.get());
            mav.addObject("pictures", visiblePictures);
            return mav;
        }
    }
}
