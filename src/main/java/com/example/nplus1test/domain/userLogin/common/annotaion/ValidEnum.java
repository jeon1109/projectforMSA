package com.example.nplus1test.domain.userLogin.common.annotaion;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.util.Arrays;

/**
 * Enum 값 검증을 위한 커스텀 어노테이션
 */
@Documented
@Constraint(validatedBy = ValidEnum.ValidEnumValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {

    String message() default "Invalid enum value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();

    // Validator 구현
    class ValidEnumValidator implements ConstraintValidator<ValidEnum, Object> {

        private Enum<?>[] enumValues;

        @Override
        public void initialize(ValidEnum annotation) {
            enumValues = annotation.enumClass().getEnumConstants();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            if (value == null) {
                return true; // null은 통과 (필수 여부는 @NotNull에서 별도 처리)
            }
            return Arrays.stream(enumValues)
                    .anyMatch(e -> e.name().equals(value.toString()));
        }
    }
}
