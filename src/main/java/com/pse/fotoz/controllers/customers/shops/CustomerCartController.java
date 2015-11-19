package com.pse.fotoz.controllers.customers.shops;

import com.pse.fotoz.dbal.entities.Cart;
import com.pse.fotoz.dbal.entities.ProductOption;
import com.pse.fotoz.helpers.forms.CartHelper;
import com.pse.fotoz.helpers.mav.ModelAndViewBuilder;
import static com.pse.fotoz.properties.LocaleUtil.getProperties;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/customers/cart")
public class CustomerCartController {
 
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView displayCart(HttpServletRequest request) {
        ModelAndView mav = ModelAndViewBuilder.empty().
                    withProperties(request).
                    build();
        
        Cart cart = CartHelper.getCurrentCart(request);
        
        cart.getOrder().getEntries().stream().
                forEach(e -> e.getOptions().setLabels(getProperties(request)));
        
        mav.addObject("cart", cart);
        
        mav.addObject("page", new Object() {
            public String lang = request.getSession().
                    getAttribute("lang").toString();
            public String uri = "/customers/cart";
            public String redirect = request.getRequestURL().toString();
        });            

        mav.setViewName("customers/shops/cart.twig");

        return mav;
    }

    /**
     * 
     * @param request
     * @return 
     */
    @RequestMapping(method = RequestMethod.POST, value = "/ajax/add")
    public ResponseEntity<String> addItem(HttpServletRequest request) {
        try {
            String data = request.getReader().lines().
                    reduce("", (s1, s2) -> s1 + s2);

            JSONObject json = new JSONObject(data);

            final int pictureId = json.getInt("picture_id");
            final int productTypeId = json.getInt("product_type_id");
            final int amount = json.getInt("amount");
            final String color = json.getString("color");
            ProductOption options = new ProductOption();            
            options.setColor(ProductOption.ColorOption.valueOf(color));
            
            Cart cart = CartHelper.getCurrentCart(request);

            CartHelper.addItemToCart(cart, pictureId, productTypeId, amount, 
                    options);
        } catch (IOException | JSONException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body("corrupt form data");
        }

        return ResponseEntity.ok().body("ok"); 
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/ajax/amount")
    public ResponseEntity<String> updateItemAmount(HttpServletRequest request) {
        try {
            String data = request.getReader().lines().
                    reduce("", (s1, s2) -> s1 + s2);

            JSONObject json = new JSONObject(data);

            final int entryId = json.getInt("entry_id");
            final int amount = json.getInt("amount");
            
            Cart cart = CartHelper.getCurrentCart(request);

            CartHelper.updateItemAmount(cart, entryId, amount);
        } catch (IOException | JSONException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body("corrupt form data");
        }

        return ResponseEntity.ok().body("ok"); 
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/ajax/remove")
    public ResponseEntity<String> removeItem(HttpServletRequest request) {
        try {
            String data = request.getReader().lines().
                    reduce("", (s1, s2) -> s1 + s2);

            JSONObject json = new JSONObject(data);

            final int entryId = json.getInt("entry_id");
            
            Cart cart = CartHelper.getCurrentCart(request);

            CartHelper.removeItemFromCart(cart, entryId);
        } catch (IOException | JSONException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body("corrupt form data");
        }

        return ResponseEntity.ok().body("ok"); 
    }
}
