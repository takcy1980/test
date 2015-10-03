package com.pse.fotoz.helpers.forms;

import com.pse.fotoz.dbal.entities.Photographer;
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
public class PhotographerValidator implements InputValidator<Photographer> {
    private final Map<String, String> properties;

    public PhotographerValidator(Map<String, String> properties) {
        this.properties = properties;
    }
    
    @Override
    public ValidationResult validate(Photographer phtgrpr) {
        final List<String> errors = new ArrayList<>();
        
        if (isEmpty(phtgrpr.getAddress())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_EMPTYFIELD"), 
                    properties.get("producer_dashboard_shops_new_form_photographer_address")));
        }
        
        if (isEmpty(phtgrpr.getCity())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_EMPTYFIELD"), 
                    properties.get("producer_dashboard_shops_new_form_photographer_city")));
        }
        
        if (isEmpty(phtgrpr.getEmail())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_EMPTYFIELD"), 
                    properties.get("producer_dashboard_shops_new_form_photographer_email")));
        }
        
        else if (!SimpleRegex.matches("@", phtgrpr.getEmail())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_INVALIDFIELD"), 
                    properties.get("producer_dashboard_shops_new_form_photographer_email")));
        }
        
        if (isEmpty(phtgrpr.getPhone())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_EMPTYFIELD"), 
                    properties.get("producer_dashboard_shops_new_form_photographer_phone")));
        }
        
        if (isEmpty(phtgrpr.getName())) {
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_EMPTYFIELD"), 
                    properties.get("producer_dashboard_shops_new_form_photographer_name")));
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
