package com.pse.fotoz.controllers.customers.account;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.entities.CustomerAccount;
import com.pse.fotoz.dbal.entities.PictureSession;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import com.pse.fotoz.helpers.users.Users;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Robert
 */
@Controller
@RequestMapping("/customers/account/")
public class CustomerAccountPictureSessionsController {
    
    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    public ModelAndView displayPictureSessions(HttpServletRequest request, 
            HttpServletResponse response) {
        ModelAndView mav = ModelAndViewBuilder.empty().                
                withProperties(request).
                withCookies(request, response).
                build();
        
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/customers/account/sessions";
            public String redirect = request.getRequestURL().toString();
        });
        
        CustomerAccount user = Users.currentUserAccount().
                orElseThrow(
                () -> new IllegalStateException("Logged in user not present "
                        + "in database."));
        
        List<PictureSession> sessions = 
                HibernateEntityHelper.all(PictureSession.class).stream().
                    filter(s -> s.getPermittedAccounts().stream().
                            anyMatch(a -> a.getId() == user.getId())).
                    collect(toList());
        
        mav.addObject("sessions", sessions);
        
        mav.setViewName("customers/account/sessions_overview.twig");
        
        return mav;
    }
}
