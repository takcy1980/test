package com.pse.fotoz.helpers.forms;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.entities.Photographer;
import com.pse.fotoz.dbal.entities.Picture;
import com.pse.fotoz.dbal.entities.Shop;
import com.pse.fotoz.helpers.forms.InputValidator.ValidationResult;
import java.util.Map;
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
    
    public static ValidationResult addShop(String login, String password, 
            String name, String address, String city, String email, 
            String phone, Map<String, String> properties) 
            throws HibernateException {
        
        Photographer phtgrpr = new Photographer();
        Shop shop = new Shop();

        phtgrpr.setAddress(address);
        phtgrpr.setCity(city);
        phtgrpr.setEmail(email);
        phtgrpr.setName(name);
        phtgrpr.setPhone(phone);

        ValidationResult pResult = new PhotographerValidator(properties).
                validate(phtgrpr);            

        shop.setLogin(login);
        shop.setPassword(password);

        ValidationResult sResult = new ShopValidator(properties).
                validate(shop);
        
        ValidationResult composed = pResult.compose(pResult, sResult);
        
        if (composed.status() == InputValidator.ValidationStatus.OK) {
            phtgrpr.persist();
            shop.setPhotographer(phtgrpr);
            shop.persist();
        }

        return composed;
    }
}
