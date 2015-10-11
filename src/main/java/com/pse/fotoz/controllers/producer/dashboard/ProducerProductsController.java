package com.pse.fotoz.controllers.producer.dashboard;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.entities.ProductType;
import com.pse.fotoz.helpers.Configuration.ConfigurationHelper;
import com.pse.fotoz.helpers.forms.InputValidator;
import com.pse.fotoz.helpers.forms.PersistenceFacade;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import com.pse.fotoz.properties.LocaleUtil;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
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
    public ModelAndView handleFileUpload(
        @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceString = request.getParameter("price");
        BigDecimal price = new BigDecimal(0);
        String stockString = request.getParameter("stock");
        int stock = 0;
        String filename = file.getOriginalFilename();
        String heightString = request.getParameter("height");
        int height = 0;
        String widthString = request.getParameter("width");
        int width = 0;
                
        List<String> errors = new ArrayList<>();
        //boolean noErrors = true;
        InputValidator.ValidationResult result;

        ModelAndView mav = ModelAndViewBuilder.empty().
                    withProperties(request).
                    build();
        mav.setViewName("producer/dashboard/products.twig");
        
        try {      
            Map<String, String> numbers = new HashMap<>();
            numbers.put("producer_dashboard_products_new_form_price", priceString);
            numbers.put("producer_dashboard_products_new_form_stock", stockString);
            numbers.put("producer_dashboard_products_new_form_height", heightString);
            numbers.put("producer_dashboard_products_new_form_width", widthString);

            result = PersistenceFacade.checkNumeric(numbers, 
                    LocaleUtil.getProperties(request));

            if(result.status() == InputValidator.ValidationStatus.OK){
                price = new BigDecimal(priceString);
                stock = Integer.parseInt(stockString);
                height = Integer.parseInt(heightString);
                width = Integer.parseInt(widthString);
            } else {
                errors.addAll(result.errors());
                mav.setViewName("producer/dashboard/products_new.twig");
                //noErrors = false;
            }
        } catch (HibernateException ex) {
            Logger.getLogger(ProducerProductsController.class.getName()).
                    log(Level.SEVERE, null, ex);
            errors.add(LocaleUtil.getProperties(request).
                    get("ERROR_INTERNALDATABASEERROR"));            
            mav.setViewName("producer/dashboard/products_new.twig");
        }
        
        try {      
            result = PersistenceFacade.addProductType(name, description, 
                    price, stock, filename, height, width, 
                    LocaleUtil.getProperties(request));

            if(result.status() == InputValidator.ValidationStatus.OK){
                //mav.setViewName("producer/dashboard/products.twig");
                List<ProductType> products = HibernateEntityHelper.all(ProductType.class);
                mav.addObject("products", products);

                //mav = displayProducts(request);

            } else {
                errors.addAll(result.errors());
                mav.setViewName("producer/dashboard/products_new.twig");
            }
        } catch (HibernateException ex) {
            Logger.getLogger(ProducerProductsController.class.getName()).
                    log(Level.SEVERE, null, ex);
            errors.add(LocaleUtil.getProperties(request).
                    get("ERROR_INTERNALDATABASEERROR"));            
            mav.setViewName("producer/dashboard/products_new.twig");
        }
        
        if (errors.isEmpty()) {
            try {
                ServletContext context = request.getServletContext();
                String appPath = context.getRealPath(ConfigurationHelper.getProductTypeAssetLocation());//dit bepaald de folder waar opgeslagen wordt
                String totalname = appPath + "\\" + filename;
                file.transferTo(new File(totalname)); 
            } catch (IOException ex){
                    Logger.getLogger(ProducerProductsController.class.getName()).
                    log(Level.SEVERE, null, ex);
                    errors.add(LocaleUtil.getProperties(request).get("ERROR_IOERROR"));
                    //noErrors = false;
                    mav.setViewName("producer/dashboard/products_new.twig");
            }
        } else {
           // errors.add(LocaleUtil.getProperties(request).get("ERROR_PRODUCER_NEWPRODUCT_NOPICTURE"));
            //noErrors = false;
           // mav.setViewName("producer/dashboard/products_new.twig");
        }
        

        

        
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
