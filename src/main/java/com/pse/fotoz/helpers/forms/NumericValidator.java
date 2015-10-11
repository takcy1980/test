package com.pse.fotoz.helpers.forms;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.math.NumberUtils;

/**
 * Validates a ProductType entity.
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
public class NumericValidator implements InputValidator<Map<String, String>> {
    private final Map<String, String> properties;

    /**
     * Creates a validator with properties containing used error messages.
     * @param properties properties containing used error messages
     */
    public NumericValidator(Map<String, String> properties) {
        this.properties = properties;
    }
    
    @Override
    public ValidationResult validate(Map<String,String> numbers) {
        final List<String> errors = new ArrayList<>();
        
	numbers.forEach((k,v)->{
		if(NumberUtils.isNumber(v) == false){
                    errors.add(MessageFormat.format(
                    properties.get("ERROR_INPUT_INVALIDFIELD"), 
                    properties.get(k)));
		}
	});      

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
