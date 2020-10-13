package com.banco.inter.desafio.exception;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1249285961937667718L;

	public NegocioException() {		
	}
	
	public NegocioException(String msg) {
		super(msg);
	}
}
