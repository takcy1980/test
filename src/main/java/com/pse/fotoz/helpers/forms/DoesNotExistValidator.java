package com.pse.fotoz.helpers.forms;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Gijs
 */
public class DoesNotExistValidator implements ConstraintValidator<DoesNotExist, String> {
    private Class entity;
    private String field;
     
    @Override
    public void initialize(DoesNotExist constraint) {
        entity = constraint.entity();
        field = constraint.field();
    }
 
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !HibernateEntityHelper.find(entity, field, value).stream().
                findAny().
                isPresent();
    }

}
