package com.banco.inter.desafio.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.banco.inter.desafio.validator.TipoNumberValidator;

@Documented
@Constraint(validatedBy = TipoNumberValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TipoNumber {
	
	String message() default "O valor informado não é um inteiro.";	
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
