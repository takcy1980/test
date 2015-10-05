package com.pse.fotoz.controllers.producer.dashboard;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.entities.ProductType;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    
    //TODO POST FORM
    
    /**
     * Handles a request to add a new product to the system by the producer.
     * @param request
     * @return 
     */
    @RequestMapping(method = RequestMethod.POST, value = "/new")
    public ModelAndView handleNewProductForm(HttpServletRequest request) {
        String name = request.getParameter("name");
        
        InputStream inStream = null;
	OutputStream outStream = null;
        
        try{  
            
            //YourClass.class.getResourceAsStream("public.key");
            
    	    //File bfile = new File("/src/main/webapp/assets/products/img/testfoto.jpg");
            //File bfile = new File("src/main/webapp/assets/products/img/testfoto.jpg");
            
    	    inStream = request.getInputStream();
    	    //outStream = new FileOutputStream(bfile);
            outStream = new FileOutputStream("testfoto.jpg");
            
    	    byte[] buffer = new byte[1024];
            
    	    int length;
    	    //copy the file content in bytes 
    	    while ((length = inStream.read(buffer)) > 0){
    	  
    	    	outStream.write(buffer, 0, length);

    	    }
    	 
    	    inStream.close();
    	    outStream.close();
    	    
    	    //delete the original file
    	    //afile.delete();
    	    
    	    System.out.println("File is copied successful!");
    	    
    	}catch(IOException e){
    	    e.printStackTrace();
    	}
        
        
        
//        String login = request.getParameter("login");
//        String password = request.getParameter("password");
//        String name = request.getParameter("name");
//        String address = request.getParameter("address");
//        String city = request.getParameter("city");
//        String email = request.getParameter("email");
//        String phone = request.getParameter("phone");
//        
//        List<String> errors = new ArrayList<>();
//        
//        ModelAndView mav = ModelAndViewBuilder.empty().
//                    withProperties(request).
//                    build();
//        
//        try {
//            InputValidator.ValidationResult result = PersistenceFacade.addShop(login, password, 
//                    name, address, city, email, phone, 
//                    LocaleUtil.getProperties(request));
//            
//            if (result.status() == InputValidator.ValidationStatus.OK) {
//                mav.setViewName("producer/dashboard/shops_new_success.twig");
//            } else {
//                errors.addAll(result.errors());
//                mav.setViewName("producer/dashboard/shops_new.twig");
//            }
//        } catch (HibernateException ex) {
//            Logger.getLogger(ProducerShopsController.class.getName()).
//                    log(Level.SEVERE, null, ex);
//            errors.add(LocaleUtil.getProperties(request).
//                    get("ERROR_INTERNALDATABASEERROR"));
//            
//            mav.setViewName("producer/dashboard/shops_new.twig");
//        }
//        
//        
//        mav.addObject("errors", errors);
//        
//        mav.addObject("page", new Object() {
//            public String lang = request.getSession().
//                    getAttribute("lang").toString();
//            public String uri = "/producer/dashboard/shops";
//            public String redirect = request.getRequestURL().toString();
//        });
        
        ModelAndView mav = ModelAndViewBuilder.empty().
                    withProperties(request).
                    build();
        mav.setViewName("producer/dashboard/products.twig");
        return mav;
    }
    
}
