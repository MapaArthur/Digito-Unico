package com.banco.inter.desafio.endpoint.exception;

import java.util.Date;
import java.util.List;

/**
 * Classe responsavel para conter informações do Erro.
 * 
 * @author arthurmapati@gmail.com
 * 
 */

public class ApiErrorList extends ApiError {

	private static final long serialVersionUID = -3782791291396036121L;
	
	private List<String> errors;
	
	public ApiErrorList(int code, String msg, Date date, List<String> errors) {
		super(code, msg, date);
		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	
}
