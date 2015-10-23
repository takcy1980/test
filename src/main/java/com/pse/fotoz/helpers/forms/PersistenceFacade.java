package com.pse.fotoz.helpers.forms;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.entities.CustomerAccount;
import com.pse.fotoz.dbal.entities.Customer;
import com.pse.fotoz.dbal.entities.Photographer;
import com.pse.fotoz.dbal.entities.Picture;
import com.pse.fotoz.dbal.entities.Shop;
import java.util.Optional;

/**
 * Facade abstracting the handling of common user actions.
 * @author Robert
 */
public class PersistenceFacade {
    /**
     * Approves a specific picture.
     * @param pictureId The identity of the picture
     * @throws HibernateException If an error occured attempting to persist the
     * modified entity
     * @throws IllegalArgumentException If no picture with given identity
     * exists.
     */
    public static void approvePicture(int pictureId) throws
            HibernateException, IllegalArgumentException {
        Optional<Picture> picture
                = HibernateEntityHelper.byId(Picture.class, pictureId);

        if (!picture.isPresent()) {
            throw new IllegalArgumentException("Given id does not match any "
                    + "picture.");
        } else {
            picture.get().setApproved(Picture.Approved.YES);
            picture.get().persist();
        }
    }

    /**
     * Rejects a specific picture.
     * @param pictureId The identity of the picture
     * @throws HibernateException If an error occured attempting to persist the
     * modified entity
     * @throws IllegalArgumentException If no picture with given identity
     * exists.
     */
    public static void rejectPicture(int pictureId) throws
            HibernateException, IllegalArgumentException {
        Optional<Picture> picture
                = HibernateEntityHelper.byId(Picture.class, pictureId);

        if (!picture.isPresent()) {
            throw new IllegalArgumentException("Given id does not match any "
                    + "picture.");
        } else {
            picture.get().setApproved(Picture.Approved.NO);
            picture.get().persist();
        }
    }

    /**
     * Adds a new shop to the system.
     *
     * @param login Login of the shop
     * @param password Password of the shop
     * @param name Name of the shop's owner
     * @param address Address of the shop's owner
     * @param city City of the shop's owner
     * @param email Email address of the shop's owner
     * @param phone Phone number of the shop's owner
     * @throws HibernateException If a persistence error occured regardless of a
     * correct input.
     */
    public static void addShop(String login, String password,
            String name, String address, String city, String email,
            String phone)
            throws HibernateException {

        Photographer phtgrpr = new Photographer();
        Shop shop = new Shop();

        phtgrpr.setAddress(address);
        phtgrpr.setCity(city);
        phtgrpr.setEmail(email);
        phtgrpr.setName(name);
        phtgrpr.setPhone(phone);

        shop.setLogin(login);
        shop.setPassword(password);
          
        phtgrpr.persist();
        shop.setPhotographer(phtgrpr);
        shop.persist();
    }
    
    /**
     * Adds a customer to the system.
     *
     * @param name Name of the shop's owner
     * @param address Address of the shop's owner
     * @param city City of the shop's owner
     * @param email Email address of the shop's owner
     * @param phone Phone number of the shop's owner
     * @param login Login of the shop
     * @param password Password of the shop
     * @throws HibernateException If a persistence error occured regardless of a
     * correct input.
     */
    public static void addCustomer(String login, String password,
            String name, String address, String city, String email,
            String phone)
            throws HibernateException {

        Customer cus = new Customer();
        CustomerAccount account = new CustomerAccount();

        cus.setName(name);
        cus.setAddress(address);
        cus.setCity(city);
        cus.setPhone(phone);
        cus.setEmail(email);

        account.setLogin(login);
        account.setPasswordHash(password);
        
        cus.persist();
        account.setCustomer(cus);
        account.persist();
    }
}
