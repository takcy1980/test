/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.helpers.forms;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.entities.Shop;
import java.time.DayOfWeek;
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
        boolean valid =  true;
        if (HibernateEntityHelper.find(
            entity, field, value).
            stream().findAny().isPresent()) {
            valid = false;
        }
        return valid;
    }

}
