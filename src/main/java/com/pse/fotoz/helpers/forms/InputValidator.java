package com.pse.fotoz.helpers.forms;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Interface describing behaviour for validating a certain input type.
 * @author Robert
 * @param <T> The type this validator validates over
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
        /**
         * The status of this result.
         * Is either OK or NOK.
         * @return Whether this result was succesful or not
         */
        public abstract ValidationStatus status();
        
        /**
         * The list of errors from validation.
         * This is only non-empty if the validation failed.
         * @return A list of errors that arose from attempting the validation.
         */
        public abstract List<String> errors();
        
        /**
         * Composes two ValidationResults.
         * Binary operator on ValidationResult.
         * The combined status is OK if both parts are OK, otherwise NOK.
         * The combined errors are the concatenation of both parts' errors.
         * Note that this can contain duplicate errors.
         * @param r1 the first ValidationResult
         * @param r2 the second ValidationResult
         * @return ValidationResult with errors from both r1 and r2 included and
         * the aggregate status
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
     * @return string == null || string.length() == 0
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
