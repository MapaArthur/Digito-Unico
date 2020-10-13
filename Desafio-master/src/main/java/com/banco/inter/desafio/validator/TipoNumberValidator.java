package com.banco.inter.desafio.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.banco.inter.desafio.annotations.TipoNumber;
import com.banco.inter.desafio.constante.MensagemConstante;

public class TipoNumberValidator implements ConstraintValidator<TipoNumber, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		char[] resultado = value.toCharArray();
		boolean inteiro = true;

		for (int i = 0; i < resultado.length; i++) {

			if (!Character.isDigit(resultado[i])) {
				return Boolean.FALSE;
			}
		}
		
		if(!value.isEmpty()) {			
			Long menorQueZero = Long.parseLong(value);
			
			if (menorQueZero <= 0 ) {
				mensagemRetorno(context, MensagemConstante.VALOR_MAIOR_QUE_ZERO);
				return Boolean.FALSE;
			}
		}

		return inteiro;
	}
	
	public ConstraintValidatorContext mensagemRetorno(ConstraintValidatorContext context, String campo) {
		context.disableDefaultConstraintViolation();
		return context.buildConstraintViolationWithTemplate(campo).addConstraintViolation();
	}
}
