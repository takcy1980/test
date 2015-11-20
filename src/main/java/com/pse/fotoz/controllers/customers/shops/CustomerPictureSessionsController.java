package com.pse.fotoz.controllers.customers.shops;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.entities.CustomerAccount;
import com.pse.fotoz.dbal.entities.Picture;
import com.pse.fotoz.dbal.entities.PictureSession;
import com.pse.fotoz.dbal.entities.Shop;
import com.pse.fotoz.helpers.forms.Parser;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import com.pse.fotoz.helpers.users.Users;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Robert
 */
@Controller
@RequestMapping("/customers/shops/{shop}/sessions/")
public class CustomerPictureSessionsController {
    
    @RequestMapping(value = "/{session}", method = RequestMethod.GET)
    public ModelAndView displayPictureSessions(@PathVariable("shop") 
            String shopid, @PathVariable("session") String sessionid,
            HttpServletRequest request) {
        ModelAndView mav = ModelAndViewBuilder.empty().
                    withProperties(request).
                    build();
        
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/customers/shops/" + sessionid + "/";
            public String redirect = request.getRequestURL().toString();
        });
        
        mav.setViewName("customers/shops/session.twig");
        
        Optional<Shop> shop = HibernateEntityHelper.find(Shop.class, 
                "login", shopid).stream()
                .findAny();
        
        int sessionId = Parser.parseInt(sessionid).
                orElse(Integer.MIN_VALUE);
        
        final Integer userid = Users.currentUserAccount().
                map(a -> a.getId()).
                orElse(Integer.MIN_VALUE);
        
        Optional<PictureSession> session = shop.isPresent() ?
                shop.get().getSessions().stream().
                        filter(s -> s.getId() == sessionId).
                        filter(s -> s.isPublic() ||
                                s.getPermittedAccounts().stream().
                                        anyMatch(a -> a.getId() == userid)).
                        findAny() :
                Optional.empty();
        
        if (!session.isPresent()) {
            return new ModelAndView("redirect:/app/customers/shops/");
        } else {
            mav.addObject("shop", shop.get());
            mav.addObject("session", session.get());
            return mav;
        }
    }
}
