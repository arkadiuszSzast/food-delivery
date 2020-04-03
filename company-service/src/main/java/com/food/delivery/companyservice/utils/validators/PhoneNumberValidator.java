package com.food.delivery.companyservice.utils.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidatorImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberValidator {

	String message() default "com.fooddelivery.constraints.phonenumber";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
