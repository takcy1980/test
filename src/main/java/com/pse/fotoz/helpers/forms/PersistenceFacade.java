package com.pse.fotoz.helpers.forms;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.entities.Customer_accounts;
import com.pse.fotoz.dbal.entities.Customers;
import com.pse.fotoz.dbal.entities.Photographer;
import com.pse.fotoz.dbal.entities.Picture;
import com.pse.fotoz.dbal.entities.ProductType;
import com.pse.fotoz.dbal.entities.Shop;
import com.pse.fotoz.helpers.Configuration.ConfigurationHelper;
import com.pse.fotoz.helpers.forms.InputValidator.ValidationResult;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.File;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletContext;
import org.springframework.web.multipart.MultipartFile;

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
     * @param properties Properties file containing the error messages that can
     * be generated.
     * @return ValidationResult that is either OK if no illegal input was
     * detected, or NOK if there was.
     * @throws HibernateException If a persistence error occured regardless of a
     * correct input.
     */
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
     * @param properties Properties file containing the error messages that can
     * be generated.
     * @return ValidationResult that is either OK if no illegal input was
     * detected, or NOK if there was.
     * @throws HibernateException If a persistence error occured regardless of a
     * correct input.
     */
    public static ValidationResult addCustomer(String login, String password,
            String name, String address, String city, String email,
            String phone, Map<String, String> properties)
            throws HibernateException {

        Customers cus = new Customers();
        Customer_accounts account = new Customer_accounts();

        cus.setName(name);
        cus.setAddress(address);
        cus.setCity(city);
        cus.setPhone(phone);
        cus.setEmail(email);
        

        ValidationResult pResult = new CustomerValidator(properties).
                validate(cus);

        account.setLogin(login);
        account.setPasswordHash(password);

        ValidationResult sResult = new CustomerAccountValidator(properties).
                validate(account);

        ValidationResult composed = pResult.compose(pResult, sResult);

        if (composed.status() == InputValidator.ValidationStatus.OK) {
            cus.persist();
            account.setCustomer(cus);
            account.persist();
        }

        return composed;
    }
    
    public static ValidationResult addProductType(String name, String description,
            BigDecimal price, int stock, String filename, int height, int width, 
            Map<String, String> properties)
            throws HibernateException {

            ProductType pt = new ProductType();
            pt.setName(name);
            pt.setDescription(description);
            pt.setPrice(price);
            pt.setStock(stock);
            pt.setFilename(filename);
            pt.setHeight(height);
            pt.setWidth(width); 

            ValidationResult result = new ProductTypeValidator(properties).
            validate(pt);

            if(result.status() == InputValidator.ValidationStatus.OK) {
                pt.persist();
            }

            return result;
    }
    
    public static ValidationResult checkNumeric(Map<String, String> numbers, 
            Map<String, String> properties)
            throws HibernateException {

            ValidationResult result = new NumericValidator(properties).
            validate(numbers);

            return result;
    }
    
}
