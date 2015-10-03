package com.pse.fotoz.helpers.ajax;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.entities.Photographer;
import com.pse.fotoz.dbal.entities.Picture;
import com.pse.fotoz.dbal.entities.Shop;
import java.util.Optional;

/**
 *
 * @author Robert
 */
public class PersistenceFacade {
    public static void approvePicture(int pictureId) throws 
            HibernateException, IllegalArgumentException {
        Optional<Picture> picture = 
                HibernateEntityHelper.byId(Picture.class, pictureId);
        
        if (!picture.isPresent()) {
            throw new IllegalArgumentException("Given id does not match any "
                    + "picture.");
        } else {
            picture.get().setApproved(Picture.Approved.YES);
            picture.get().persist();
        }
    }
    
    public static void rejectPicture(int pictureId) throws 
            HibernateException, IllegalArgumentException {
        Optional<Picture> picture = 
                HibernateEntityHelper.byId(Picture.class, pictureId);
        
        if (!picture.isPresent()) {
            throw new IllegalArgumentException("Given id does not match any "
                    + "picture.");
        } else {
            picture.get().setApproved(Picture.Approved.NO);
            picture.get().persist();
        }
    }
    
    public static void addShop(String login, String password, String name, 
            String address, String city, String email, String phone) 
            throws IllegalArgumentException, HibernateException {
        
        if (HibernateEntityHelper.find(Shop.class, "login", login).stream().
                findAny().isPresent()) {
            throw new IllegalArgumentException("A shop with that login already"
                    + " exists.");
        } else {
            Photographer phtgrpr = new Photographer();
            Shop shop = new Shop();
            
            phtgrpr.setAddress(address);
            phtgrpr.setCity(city);
            phtgrpr.setEmail(email);
            phtgrpr.setName(name);
            phtgrpr.setPhone(phone);
            
            phtgrpr.persist();
            
            shop.setLogin(login);
            shop.setPassword(password);
            shop.setPhotographer(phtgrpr);
            
            shop.persist();
        }
    }
}
