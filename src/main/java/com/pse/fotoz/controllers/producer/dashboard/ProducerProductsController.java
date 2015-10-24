package com.pse.fotoz.controllers.producer.dashboard;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.entities.Photographer;
import com.pse.fotoz.dbal.entities.ProductType;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller handling the display of product dashboard for producer.
 * @author Gijs
 */
@Controller
@RequestMapping("/producer/dashboard/products")
public class ProducerProductsController {

    /**
     * Displays all the products to the producers.
     * @param request
     * @return 
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView displayProducts(HttpServletRequest request) {
        ModelAndView mav = ModelAndViewBuilder.empty().
                    withProperties(request).
                    build();
        
        List<ProductType> products = HibernateEntityHelper.all(ProductType.class);

        mav.addObject("products", products);
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/producer/dashboard/products";
            public String redirect = request.getRequestURL().toString();
        });
        mav.setViewName("producer/dashboard/products.twig");

        return mav;
    }
    
    /**
     * Displays a form to add new products to the system to the producer.
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
            public String uri = "/producer/dashboard/products";
            public String redirect = request.getRequestURL().toString();
        });
        mav.setViewName("producer/dashboard/products_new.twig");

        return mav;
    }
    
    //TODO: extensie checken
    @RequestMapping(method = RequestMethod.POST, value = "/new")
    @ResponseBody
    public ModelAndView handleFileUpload( @ModelAttribute(value="newProdType") @Valid ProductType newProdType, 
                    BindingResult resultProdType,
                    HttpServletRequest request,
                    @RequestParam("file") MultipartFile file) {
//        
//         public ModelAndView handleFileUpload( @Validated(ProductType.ValidationStepOne.class) ProductType productType, 
//                    Errors errs,
//                    HttpServletRequest request,
//                    @RequestParam("file") MultipartFile file) {
      
        List<String> errors = new ArrayList<>();
        
        for(FieldError e : resultProdType.getFieldErrors()){
            errors.add(e.getDefaultMessage());
        }

        ModelAndView mav = ModelAndViewBuilder.empty().
                    withProperties(request).
                    build();
        mav.setViewName("producer/dashboard/products_new.twig");
        
        mav.addObject("errors", errors);
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/producer/dashboard/products";
            public String redirect = request.getRequestURL().toString();
        });
        
        return mav;

    }  
    
}
