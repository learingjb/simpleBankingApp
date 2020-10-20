package com.simpleBankingApp.utility;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidatePassword, Object> {

    private String passwordField;
    private String confirmPasswordField;
    private String regex;

    @Override
    public void initialize(ValidatePassword constraintAnnotation) {
        passwordField = constraintAnnotation.passwordField();
        confirmPasswordField = constraintAnnotation.confirmPasswordField();
        regex = constraintAnnotation.regex();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        Object password = beanWrapper.getPropertyValue(passwordField);
        Object confirmPassword = beanWrapper.getPropertyValue(confirmPasswordField);
        if (Objects.equals(password, confirmPassword)) {
            return Pattern.compile(regex).matcher(String.valueOf(password)).matches();
        }
        return false;
    }
}
