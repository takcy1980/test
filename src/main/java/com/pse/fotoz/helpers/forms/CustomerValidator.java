package com.pse.fotoz.helpers.forms;

import com.pse.fotoz.dbal.entities.Customers;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates a Photographer entity.
 * Validator checks whether:
 * <ol>
 * <li>
 * The address, name, city, email and phone are not empty
 * </li> 
 * <li>
 * The email contains an @ sign
 * </li>
 * </ol>
 * 
 * Does NOT check whether the photographer exists
 * @author Robert
 */
public class CustomerValidator implements InputValidator<Customers> {
    private final Map<String, String> properties;

    /**
     * Creates a validator with properties containing used error messages.
     * @param properties properties containing used error messages
     */
    public CustomerValidator(Map<String, String> properties) {
        this.properties = properties;
    }
    
    @Override
    public ValidationResult validate(Customers cus) {
        final List<String> errors = new ArrayList<>();
        
        if (isEmpty(cus.getAddress())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_EMPTYFIELD"), 
                    properties.get("customer_register_adress_label")));
        }
        
        if (isEmpty(cus.getCity())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_EMPTYFIELD"), 
                    properties.get("customer_register_city_label")));
        }
        
        if (isEmpty(cus.getEmail())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_EMPTYFIELD"), 
                    properties.get("customer_register_email_label")));
        }
        
        else if (!SimpleRegex.matches("@", cus.getEmail())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_INVALIDFIELD"), 
                    properties.get("customer_register_email_label")));
        }
        
        if (isEmpty(cus.getPhone())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_EMPTYFIELD"), 
                    properties.get("customer_register_phone_label")));
        }
        
        if (isEmpty(cus.getName())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_EMPTYFIELD"), 
                    properties.get("customer_login_loginname_label")));
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
