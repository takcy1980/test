package com.pse.fotoz.helpers.Authentication;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 *
 * @author Gijs
 */
public class RedirectAuthenticationSuccesHandler  extends SavedRequestAwareAuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
            HttpServletResponse response, Authentication authentication) throws 
            IOException, ServletException {
        if (request.getParameter("redirect") != null) {
            redirectStrategy.sendRedirect(request, response, 
                request.getParameter("redirect"));
        }
        else {
           
            super.onAuthenticationSuccess(request, response, authentication);

        }
    }
    
    @Override
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    
    @Override
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}

