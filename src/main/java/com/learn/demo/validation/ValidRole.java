package com.learn.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoleValidator.class)   // ← links to validation logic
@Target({ ElementType.FIELD })                    // ← can only be placed on fields
@Retention(RetentionPolicy.RUNTIME)               // ← must be visible at runtime for reflection
public @interface ValidRole {

    String message() default "Role must be one of: JUNIOR, SENIOR, LEAD";

    Class<?>[] groups() default {};       // required by Bean Validation spec
    Class<? extends Payload>[] payload() default {};  // required by Bean Validation spec
}