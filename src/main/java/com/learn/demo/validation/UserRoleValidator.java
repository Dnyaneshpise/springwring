package com.learn.demo.validation;

import com.learn.demo.entity.UserEntity;
import com.learn.demo.validation.ValidUserRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class UserRoleValidator implements ConstraintValidator<ValidUserRole, UserEntity> {
    //                                                                          ↑
    //                                                              whole object, not just one field

    @Override
    public boolean isValid(UserEntity user, ConstraintValidatorContext context) {
        if (user == null) return true;
        if (user.getExperience() > 5) {
            return user.getRole().equals("SENIOR") || user.getRole().equals("LEAD");
        }
        return true;    // experience ≤ 5, any role is fine
    }
}