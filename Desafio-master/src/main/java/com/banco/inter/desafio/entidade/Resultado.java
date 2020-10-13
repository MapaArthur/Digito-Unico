package com.banco.inter.desafio.entidade;

import java.io.Serializable;

public class Resultado implements Serializable {

	private static final long serialVersionUID = 2708961436458088686L;
	
	private Long numero;
	
	private Long multiplicador;
	
	private Long digitoUnico;	

	private Usuario usuario;	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Long getMultiplicador() {
		return multiplicador;
	}

	public void setMultiplicador(Long multiplicador) {
		this.multiplicador = multiplicador;
	}

	public Long getDigitoUnico() {
		return digitoUnico;
	}

	public void setDigitoUnico(Long digitoUnico) {
		this.digitoUnico = digitoUnico;
	}	
	
}
