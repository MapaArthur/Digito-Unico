package com.banco.inter.desafio.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 9170537093480778246L;
	
	public NotFoundException(String msg) {
		super(msg);
	}

}
