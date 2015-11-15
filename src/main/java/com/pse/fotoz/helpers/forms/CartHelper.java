package com.pse.fotoz.helpers.forms;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.entities.Cart;
import com.pse.fotoz.dbal.entities.OrderEntry;
import com.pse.fotoz.dbal.entities.Picture;
import com.pse.fotoz.dbal.entities.ProductOption;
import com.pse.fotoz.dbal.entities.ProductType;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

/**
 * Helper class to handle Cart manipulations.
 * Note that Carts are not a persistent object and thus the methods provided 
 * have no interaction with the database unless specified.
 * @author Robert
 */
public class CartHelper {
    
    /**
     * Adds an item (order entry) to a cart.
     * @param cart The cart the item should be added to.
     * @param pictureId The id of the Picture.
     * @param productTypeId The id of the ProductType.
     * @param amount The amount of items added.
     * @param options The ProductOption to associate with this item.
     * @throws IllegalArgumentException If the Picture or ProductType id does
     * not point to an existing entity or if the amount is non-positive
     */
    public static void addItemToCart(Cart cart, int pictureId, 
            int productTypeId, int amount, ProductOption options) 
            throws IllegalArgumentException {
        if (amount < 1) {
            throw new IllegalArgumentException("Non-positive amount of items "
                    + "to add.");
        }
        
        Picture picture = HibernateEntityHelper.byId(Picture.class, pictureId).
                orElseThrow(() -> new IllegalArgumentException("Picture does "
                        + "not exist."));
        
        ProductType productType = HibernateEntityHelper.byId(ProductType.class, 
                productTypeId).
                orElseThrow(() -> new IllegalArgumentException("Product type "
                        + "does not exist."));
        
        OrderEntry entry = new OrderEntry();
        
        entry.setOrder(cart.getOrder());
        entry.setPicture(picture);
        entry.setType(productType);
        entry.setOptions(options);        
        entry.setAmount(amount);
        entry.setTotalPrice(amount * ( productType.getPrice().doubleValue() + 
                picture.getPrice().doubleValue() ));
        
        cart.addOrderEntry(entry);
    }
    
    /**
     * Returns the Cart associated with the given request, or adds a new, empty 
     * Cart to request session.
     * @param request
     * @return 
     */
    public static Cart getCurrentCart(HttpServletRequest request) {
        Cart cart = new Cart();
        
        try {
            if ((Cart) request.getSession().getAttribute("cart") == null) {
                request.getSession().setAttribute("cart", cart);
            } else {
                cart = (Cart) request.getSession().getAttribute("cart");
            }
        } catch (ClassCastException ex) {
            request.getSession().setAttribute("cart", cart);
        }
        
        return cart;       
    }
    
    /**
     * Resets the cart instance associated with the request.
     * @param request
     * @return 
     */
    public static Cart flushCart(HttpServletRequest request) {
        Cart cart = new Cart();
        
        request.getSession().setAttribute("cart", cart);
        
        return cart;
    }
}
