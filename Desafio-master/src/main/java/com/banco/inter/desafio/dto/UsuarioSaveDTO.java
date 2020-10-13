package com.banco.inter.desafio.dto;

import com.banco.inter.desafio.entidade.Usuario;

/**
 * DTO responsável por conter as informações do Usuário com ID
 * 
 * @author arthurmapati@gmail.com
 * 
 */

public class UsuarioSaveDTO extends UsuarioDTO {

	private static final long serialVersionUID = -7916255556805541727L;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public static Usuario mapper(UsuarioSaveDTO usuarioDTO) {
		Usuario usuario = new Usuario();		
	
		usuario.setId(usuarioDTO.getId());
		usuario.setNome(usuarioDTO.getNome());
		usuario.setEmail(usuarioDTO.getEmail());
		
		return usuario;
	}
	
	public static UsuarioSaveDTO mapper(Usuario usuario) {
		UsuarioSaveDTO usuarioDTO = new UsuarioSaveDTO();		
		
		usuarioDTO.setId(usuario.getId());
		usuarioDTO.setNome(usuario.getNome());
		usuarioDTO.setEmail(usuario.getEmail());
		
		return usuarioDTO;
	}

}
