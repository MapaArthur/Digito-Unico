package com.banco.inter.desafio.dto;

import java.io.Serializable;

public class ChaveUsuarioDTO implements Serializable {

	private static final long serialVersionUID = -6191881017899348019L;
	
	private Long id;
	
	private String chavePublica;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChavePublica() {
		return chavePublica;
	}

	public void setChavePublica(String chavePublica) {
		this.chavePublica = chavePublica;
	}

}
