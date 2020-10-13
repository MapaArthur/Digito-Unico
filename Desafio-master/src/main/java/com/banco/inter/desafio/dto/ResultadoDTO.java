package com.banco.inter.desafio.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.banco.inter.desafio.annotations.TipoNumber;
import com.banco.inter.desafio.entidade.Resultado;

/**
 * DTO responsável por conter as informações do Resultado do Digito Unico
 * 
 * @author arthurmapati@gmail.com
 * 
 */

public class ResultadoDTO  implements Serializable {

	private static final long serialVersionUID = 4506473276996717976L;
	
	@NotBlank(message = "Campo Obrigatório")
	@TipoNumber
	private String numero;
	
	@NotBlank(message = "Campo Obrigatório")
	@TipoNumber
	private String multiplicador;
	
	private Long valorBase;

	private Long digitoUnico;
	
	private UsuarioSaveDTO usuarioDTO;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getMultiplicador() {
		return multiplicador;
	}

	public void setMultiplicador(String multiplicador) {
		this.multiplicador = multiplicador;
	}	
	
	public Long getValorBase() {
		return valorBase;
	}

	public void setValorBase(Long valorBase) {
		this.valorBase = valorBase;
	}
	
	public Long getDigitoUnico() {
		return digitoUnico;
	}

	public void setDigitoUnico(Long digitoUnico) {
		this.digitoUnico = digitoUnico;
	}
	
	public Resultado mapper(ResultadoDTO resultadoDTO) {
		Resultado resultado = new Resultado();
		
		resultado.setNumero(Long.parseLong(resultadoDTO.numero));
		resultado.setMultiplicador(Long.parseLong(resultadoDTO.getMultiplicador()));
		resultado.setDigitoUnico(resultadoDTO.getDigitoUnico());
		
		return resultado;		
	}

	public UsuarioSaveDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioSaveDTO(UsuarioSaveDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

}
