package com.learn.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserRoleValidator.class)
@Target({ ElementType.TYPE })          // ← CLASS level, not field level
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserRole {
    String message() default "Senior experience requires SENIOR or LEAD role";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}