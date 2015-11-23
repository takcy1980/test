package com.pse.fotoz.controllers.common;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PaymentCreateController {
 
    @RequestMapping(method = RequestMethod.GET, path = "/payment/pay/{orderId}")
    public RedirectView createPayment(HttpServletRequest request, 
            HttpServletResponse response, @PathVariable String orderId) {
            

    

        return new RedirectView("http://www.google.nl");
    }

}
