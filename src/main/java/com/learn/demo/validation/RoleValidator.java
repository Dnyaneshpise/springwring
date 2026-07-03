package com.learn.demo.validation;

import com.learn.demo.validation.ValidRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {
    //                                                      ↑          ↑
    //                                           annotation type    field type it validates

    private static final List<String> VALID_ROLES = List.of("JUNIOR", "SENIOR", "LEAD");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;                    // null check first
        return VALID_ROLES.contains(value.toUpperCase());   // case-insensitive check
    }
}