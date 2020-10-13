package com.banco.inter.desafio.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.banco.inter.desafio.entidade.Usuario;

/**
 * DTO responsável por conter as informações do Usuário
 * 
 * @author arthurmapati@gmail.com
 * 
 */

public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = -165061144646744678L;	
	
	@NotBlank(message = "Nome Obrigatório")
	private String nome;
	
	@Email(message = "E-mail não valido")
	@NotBlank(message = "E-mail Obrigatório")
	private String email;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public static Usuario mapper(UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario();		
	
		usuario.setNome(usuarioDTO.getNome());
		usuario.setEmail(usuarioDTO.getEmail());
		
		return usuario;
	}
	
	public static UsuarioDTO mapper(Usuario usuario) {
		UsuarioDTO usuarioDTO = new UsuarioDTO();		
		
		usuarioDTO.setNome(usuario.getNome());
		usuarioDTO.setEmail(usuario.getEmail());
		
		return usuarioDTO;
	}

}
