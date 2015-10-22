package com.pse.fotoz.helpers.forms;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author Gijs
 */
@Documented
@Constraint(validatedBy = DoesNotExistValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface DoesNotExist {
    
    String message() default "{0} bestaat al";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    Class entity();
    
    String field();
}
