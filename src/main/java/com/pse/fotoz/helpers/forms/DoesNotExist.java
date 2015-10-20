/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.helpers.forms;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import static javafx.scene.input.KeyCode.T;
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
