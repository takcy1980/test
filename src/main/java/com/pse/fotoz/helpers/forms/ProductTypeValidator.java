package com.pse.fotoz.helpers.forms;

import com.pse.fotoz.dbal.entities.ProductType;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates a ProductType entity.
 * Validator checks whether:
 * <ol>
 * <li>
 * The name, description and filename are not empty
 * </li> 
 * </ol>
 * 
 * Does NOT check whether the product type exists
 * @author Gijs
 */
public class ProductTypeValidator implements InputValidator<ProductType> {
    private final Map<String, String> properties;

    /**
     * Creates a validator with properties containing used error messages.
     * @param properties properties containing used error messages
     */
    public ProductTypeValidator(Map<String, String> properties) {
        this.properties = properties;
    }
    
    @Override
    public ValidationResult validate(ProductType pt) {
        final List<String> errors = new ArrayList<>();
        
        if(isEmpty(pt.getName())){
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_EMPTYFIELD"), 
                    properties.get("producer_dashboard_products_new_form_name")));
        }
        
        if(isEmpty(pt.getDescription())){
            errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_EMPTYFIELD"), 
                    properties.get("producer_dashboard_products_new_form_description")));
        }
        
        if(isEmpty(pt.getFilename())){
            errors.add(properties.get("ERROR_PRODUCER_NEWPRODUCT_NOPICTURE"));       
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
