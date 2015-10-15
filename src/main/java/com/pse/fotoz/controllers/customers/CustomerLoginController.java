package com.pse.fotoz.controllers.customers;

import com.pse.fotoz.controllers.producer.dashboard.ProducerShopsController;
import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.entities.CustomerAccount;
import com.pse.fotoz.dbal.entities.Shop;
import com.pse.fotoz.helpers.encryption.PasswordHash;
import com.pse.fotoz.helpers.forms.InputValidator;
import com.pse.fotoz.helpers.forms.InputValidator.ValidationResult;
import com.pse.fotoz.helpers.forms.PersistenceFacade;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import com.pse.fotoz.properties.LocaleUtil;
import static java.nio.file.Files.list;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/customers/login")
public class CustomerLoginController {

    

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadLoginScreen(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        Map<String, String> labels;

        try {
            labels = LocaleUtil.getProperties(
                    request.getSession().getAttribute("lang").toString());
        } catch (IllegalArgumentException | NullPointerException e) {
            request.getSession().setAttribute("lang", "nl");
            labels = LocaleUtil.getProperties(
                    request.getSession().getAttribute("lang").toString());
        }

        mav.addObject("labels", labels);
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String redirect = request.getRequestURL().toString();
        });

        mav.setViewName("customers/login/login.twig");

        return mav;
    }

    /**
     * Displays a form to add new customer to the system to the customer.
     *
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public ModelAndView provideNewShopForm(HttpServletRequest request) {
        ModelAndView mav = ModelAndViewBuilder.empty().
                withProperties(request).
                build();

        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/customers/login/login";
            public String redirect = request.getRequestURL().toString();
        });
        mav.setViewName("customers/login/register.twig");

        return mav;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView serviceLoginRequest(HttpServletRequest request, HttpServletResponse res) {
        ModelAndView mav = new ModelAndView();

        String name = request.getParameter("login");
        String password = request.getParameter("password");
        
        List<CustomerAccount> list = HibernateEntityHelper.all(CustomerAccount.class);
        
        boolean login = false;
        
        for(CustomerAccount cus : list)
        {
           if (cus.validatePassword(password)&& cus.getLogin().equals(name)) {
               
                System.err.println("Test");
                login = true;
                mav.setViewName("customers/login/loginSession.twig");
            }
        }

        // mav.setViewName("producer/login/login.twig");
        mav.addObject("labels", LocaleUtil.getProperties("en"));
        mav.addObject("page", new Object() {
            public String lang = "en";
        });
        
         if(!login)
         {
                mav.setViewName("common/error/505.jsp");
         }
            

        return mav;
    }

    /**
     * Handles a request to add a new shop to the system by the producer.
     *
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/new")
    public ModelAndView handleNewCustomerForm(HttpServletRequest request) {

        String name = request.getParameter("customer_name");
        String address = request.getParameter("customer_adress");
        String city = request.getParameter("customer_city");
        String phone = request.getParameter("customer_phone");
        String email = request.getParameter("customer_email");

        String login = request.getParameter("login");
        String password = request.getParameter("inputPassword");

        List<String> errors = new ArrayList<>();

        ModelAndView mav = ModelAndViewBuilder.empty().
                withProperties(request).
                build();

        try {
            ValidationResult result = PersistenceFacade.addCustomer(login, password,
                    name, address, city, email, phone,
                    LocaleUtil.getProperties(request));

            if (result.status() == InputValidator.ValidationStatus.OK) {
                mav.setViewName("customers/login/customer_new_success.twig");
            } else {
                errors.addAll(result.errors());
                mav.setViewName("customers/login/register.twig");
            }
        } catch (HibernateException ex) {
            Logger.getLogger(ProducerShopsController.class.getName()).
                    log(Level.SEVERE, null, ex);
            errors.add(LocaleUtil.getProperties(request).
                    get("ERROR_INTERNALDATABASEERROR"));

            mav.setViewName("customers/login/register.twig");
        }

        mav.addObject("errors", errors);

        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/customers/login";
            public String redirect = request.getRequestURL().toString();
        });

        return mav;
    }
}
