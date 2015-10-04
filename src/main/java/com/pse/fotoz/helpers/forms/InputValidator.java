package com.pse.fotoz.helpers.forms;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Robert
 */
public interface InputValidator<T> {
    /**
     * Validates an input T.
     * @param t the input
     * @return ValidationResult either OK or NOK with errors
     */
    public abstract ValidationResult validate(T t);

    /**
     * Interface describing the result of a validating process.
     * 
     * Representation invariants:
     * The result is either OK or NOK.
     * The result is NOK iff it has at least one error.
     */
    public interface ValidationResult {
        public abstract ValidationStatus status();
        
        public abstract List<String> errors();
        
        /**
         * Composes two ValidationResults.
         * Binary operator on ValidationResult.
         * The combined status is OK if both parts are OK, otherwise NOK.
         * The combined errors are the concatenation of both parts' errors.
         * Note that this can contain duplicate errors.
         * @param r1 the first ValidationResult
         * @param r2 the second ValidationResult
         * @return 
         */
        public default ValidationResult compose(ValidationResult r1, 
                ValidationResult r2) {
            return new ValidationResult() {

                @Override
                public ValidationStatus status() {
                    return r1.status() == ValidationStatus.NOK || 
                            r2.status() == ValidationStatus.NOK ?
                            ValidationStatus.NOK : ValidationStatus.OK;
                }

                @Override
                public List<String> errors() {
                    return Stream.concat(r1.errors().stream(), 
                            r2.errors().stream()).
                            collect(Collectors.toList());
                }
            };
        }
    }

    /**
     * Returns whether a string is empty or null.
     * @param string
     * @return 
     */
    default boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }
    
    /**
     * Enum representing OK and NOK status of a ValidationResult.
     */
    public static enum ValidationStatus {
        OK, NOK
    }
}
