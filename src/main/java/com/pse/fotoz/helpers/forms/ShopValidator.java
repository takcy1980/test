package com.pse.fotoz.helpers.forms;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.entities.Photographer;
import com.pse.fotoz.dbal.entities.Shop;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates a Shop entity.
 * Validator checks whether:
 * <ol>
 * <li>
 * The shop doesn't already exist
 * </li> 
 * <li>
 * The password and login are not empty
 * </li>
 * <li>
 * \@Issue lies
 * The password is at least 8 characters long, only alphanumeric character.
 * </li>
 * </ol>
 * 
 * Does NOT check whether the photographer exists
 * @author Robert
 */
public class ShopValidator implements InputValidator<Shop> {
    private final Map<String, String> properties;

    public ShopValidator(Map<String, String> properties) {
        this.properties = properties;
    }
    
    @Override
    public ValidationResult validate(Shop shop) {
        final List<String> errors = new ArrayList<>();
        
        if (HibernateEntityHelper.find(
                Shop.class, "login", shop.getLogin()).
                stream().findAny().isPresent()) {
            errors.add(properties.
                    get("ERROR_PRODUCER_NEWSHOP_LOGINALREADYEXISTS"));
        }
        
        if (isEmpty(shop.getLogin())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_EMPTYFIELD"), 
                    properties.get("producer_dashboard_shops_new_form_store_login")));
        }
        
        if (isEmpty(shop.getPasswordHash())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_EMPTYFIELD"), 
                    properties.get("producer_dashboard_shops_new_form_store_password")));
        } 
        //@Issue no hashing should happen until the database is involved
        else if (//shop.getPasswordHash().length() < 8 || 
                !SimpleRegex.matches("[a-zA-Z0-9]*", shop.getPasswordHash())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_INVALIDFIELD"), 
                    properties.get("producer_dashboard_shops_new_form_store_password")));
        }
        
        return new ValidationResult() {

            @Override
            public ValidationStatus status() {
                return errors.isEmpty() ? ValidationStatus.OK : 
                        ValidationStatus.NOK;
            }

            @Override
            public List<String> errors() {
                return errors;
            }
        };
    }

}
