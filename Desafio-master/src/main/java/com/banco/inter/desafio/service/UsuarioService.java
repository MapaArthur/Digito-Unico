package com.banco.inter.desafio.service;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.inter.desafio.constante.MensagemConstante;
import com.banco.inter.desafio.dao.UsuarioDAO;
import com.banco.inter.desafio.dto.ChaveUsuarioDTO;
import com.banco.inter.desafio.dto.UsuarioSaveDTO;
import com.banco.inter.desafio.entidade.Usuario;
import com.banco.inter.desafio.exception.NegocioException;
import com.banco.inter.desafio.exception.NotFoundException;
import com.banco.inter.desafio.security.SecurityConfigAES;
import com.banco.inter.desafio.security.SecurityConfigRSA;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioDAO dao;
		
	private SecurityConfigRSA securityRSA;
	private SecurityConfigAES securityAES;
	
	private static final Map<ChaveUsuarioDTO, KeyPair> mapChave = new HashMap<>();
	
	/**
	 * Método responsável por salvar um Usuario
	 *
	 * @param {@link Usuario}
	 * 
	 * @author arthurmapati@gmail.com
	 *
	 * @return {@link UsuarioSaveDTO} - DTO do Usuario
	 *
	 * @throws NoSuchAlgorithmException - Erro ao criar as chaves publicas e privada.
	 * @throws InvalidKeyException - Erro inesperado ao criptografar.
	 * @throws NoSuchPaddingException - Erro inesperado ao criptografar.
	 * @throws BadPaddingException - Erro inesperado ao criptografar.
	 * @throws IllegalBlockSizeException - Erro inesperado ao criptografar.	 
	 */
	
	public UsuarioSaveDTO salvar(Usuario u) {
		Usuario usuario = new Usuario();
		ChaveUsuarioDTO chaveUsuarioDTO = new ChaveUsuarioDTO();
		try {
			
			SecretKey chave = gerarChave();
			securityAES = new SecurityConfigAES(chave);			
			String nomeCriptografado = securityAES.criptografar(u.getNome());
			String emailCriptografado = securityAES.criptografar(u.getEmail());			
			
			u.setNome(nomeCriptografado);
			u.setEmail(emailCriptografado);
			usuario = dao.save(u);
			chaveUsuarioDTO.setId(usuario.getId());			
			addChaveCriptografada(chave, chaveUsuarioDTO);
		} catch (NoSuchAlgorithmException e) {			
			throw new NegocioException(MensagemConstante.ERRO_AO_GERAR_CHAVE + e);
		} catch (InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {			
			throw new NegocioException(MensagemConstante.ERRO_NA_CRIPTOGRAFIA + e);
		}
		
		return UsuarioSaveDTO.mapper(usuario);	
	}
	
	/**
	 * Método responsável por vincular chave secreta com o usuario 	
	 * 
	 * @author arthurmapati@gmail.com
	 *
	 * @param {@link chave} SecretKey chave publica do usuario
	 * @param {@link chaveUsuarioDTO} DTO para armazenar a chave do usuario
	 */

	
	private void addChaveCriptografada(SecretKey chave, ChaveUsuarioDTO chaveUsuarioDTO) {
		KeyPair parChave;
		try {
			parChave = SecurityConfigRSA.getParChave();
			securityRSA = new SecurityConfigRSA(parChave.getPublic(), parChave.getPrivate());
			String chaveCriptografada = securityRSA.criptografar(chave.getEncoded());
			chaveUsuarioDTO.setChavePublica(chaveCriptografada);
			mapChave.put(chaveUsuarioDTO, parChave);
		
		} catch (NoSuchAlgorithmException e) {			
			throw new NegocioException(MensagemConstante.ERRO_AO_GERAR_CHAVE + e);
		} catch (InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {			
			throw new NegocioException(MensagemConstante.ERRO_NA_CRIPTOGRAFIA + e);
		}
	}
	
	/**
	 * Método responsável por gerar a chave publica 	
	 * 
	 * @author arthurmapati@gmail.com
	 *
	 * @return {@link SecretKey} - Chave Publica para criptografar
	 */
	
	private SecretKey gerarChave() {
		SecretKey secretKey = null;
		try {
			secretKey = SecurityConfigAES.getChave();
		} catch (NoSuchAlgorithmException e) {			
			throw new NegocioException(MensagemConstante.ERRO_AO_GERAR_CHAVE + e);
		}
		
		return secretKey;
	}
	
	/**
	 * Método responsável por listar todos os Usuarios cadastrados	
	 * 
	 * @author arthurmapati@gmail.com
	 *
	 * @return {@link List<UsuarioSaveDTO>} - Lista dos usuarios cadastrados
	 */
	
	public List<UsuarioSaveDTO> listarTodos(){
		List<Usuario> usuarios = dao.findAll();
		List<UsuarioSaveDTO> usuariosDTO = usuarios.stream().map(usuario -> UsuarioSaveDTO.mapper(usuario)).collect(Collectors.toList());
		
		return usuariosDTO;
	}
	
	/**
	 * Método responsável por atualizar um Usuario
	 *
	 * @param {@link Usuario}
	 * 
	 * @author arthurmapati@gmail.com
	 *
	 * @return {@link UsuarioSaveDTO} - DTO do Usuario
	 *
	 * @throws NoSuchAlgorithmException - Erro ao criar as chaves publicas e privada.
	 * @throws InvalidKeyException - Erro inesperado ao criptografar.
	 * @throws NoSuchPaddingException - Erro inesperado ao criptografar.
	 * @throws BadPaddingException - Erro inesperado ao criptografar.
	 * @throws IllegalBlockSizeException - Erro inesperado ao criptografar.	 
	 */
	
	public UsuarioSaveDTO atualiza(Usuario u) {
		Usuario usuario = new Usuario();
		String chavePublica = recuperarChave(u.getId());
		KeyPair parChaves = recuperarChaves(u.getId());
		securityRSA = new SecurityConfigRSA(parChaves.getPublic(), parChaves.getPrivate());	

		try {
			byte[] chaveDescriptografada = securityRSA.descriptografar(chavePublica);
			securityAES = new SecurityConfigAES(chaveDescriptografada);
			
			String nomeCriptografado = securityAES.criptografar(u.getNome());
			String emailCriptografado = securityAES.criptografar(u.getEmail());
			
			u.setNome(nomeCriptografado);
			u.setEmail(emailCriptografado);			
			usuario = dao.save(u);
			
		} catch (NoSuchAlgorithmException e) {			
			throw new NegocioException(MensagemConstante.ERRO_AO_GERAR_CHAVE + e);
		} catch (InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {			
			throw new NegocioException(MensagemConstante.ERRO_NA_CRIPTOGRAFIA + e);
		}
		
		return UsuarioSaveDTO.mapper(usuario);		
	}
	
	/**
	 * Método responsável por recuperar o usuário
	 *
	 * @param {@link String chave} - Chave publica do Usuário
	 * 
	 * @author arthurmapati@gmail.com
	 *
	 * @return {@link ChaveUsuarioDTO} - DTO com as informações da chave do usuário
	 */
	
	private ChaveUsuarioDTO recuperarChaveUsuario(String chave) {
		ChaveUsuarioDTO chaveUsuarioDTO = null;
		Set<ChaveUsuarioDTO> chaves = mapChave.keySet();

		for (Iterator<ChaveUsuarioDTO> iterator = chaves.iterator(); iterator.hasNext();) {
			chaveUsuarioDTO = iterator.next();
			if(chaveUsuarioDTO.getChavePublica().equals(chave.trim())) {
				return chaveUsuarioDTO;
			}			
		}
		return chaveUsuarioDTO;
	}
	
	/**
	 * Método responsável por recuperar a chave Privada e Publica
	 *
	 * @param {@link Long id} - Id do Usuario
	 * 
	 * @author arthurmapati@gmail.com
	 *
	 * @return {@link String} - Chave publica
	 */
	
	private String recuperarChave(Long id) {
		String chavePublica = null;
		Set<ChaveUsuarioDTO> chaves = mapChave.keySet();

		for(ChaveUsuarioDTO chaveUsuarioDTO : chaves)
		{
			if(chaveUsuarioDTO.getId().equals(id)) {				
				chavePublica = chaveUsuarioDTO.getChavePublica();
				break;
			}			
		}
		return chavePublica;
	}
	
	/**
	 * Método responsável por recuperar a chave Privada e Publica
	 *
	 * @param {@link Long id} - Id do Usuario
	 * 
	 * @author arthurmapati@gmail.com
	 *
	 * @return {@link String} - Chave publica
	 */
	
	private KeyPair recuperarChaves(Long id) {
		KeyPair parChaves = null;
		Set<ChaveUsuarioDTO> chaves = mapChave.keySet();

		for(ChaveUsuarioDTO chaveUsuarioDTO : chaves)
		{
			if(chaveUsuarioDTO.getId().equals(id)) {				
				parChaves = mapChave.get(chaveUsuarioDTO);
				break;
			}			
		}
		return parChaves;
	}
	

	/**
	 * Método responsável por recuperar a chave Publica
	 *
	 * @param {@link Long id} - Id do Usuario
	 * 
	 * @author arthurmapati@gmail.com
	 *
	 * @return {@link String} - Chave publica
	 */
	
	public String recuperarChavePorUsuario(Long id) {
		String chave = recuperarChave(id);					
		return chave;		
	}
	
	/**
	 * Método responsável por remover um Usuario
	 *
	 * @param {@link usuario} - Usuario
	 * 
	 * @author arthurmapati@gmail.com
	 *	 
	 */
	
	public void removerUsuario(Usuario usuario) {
		dao.delete(usuario);
	}
	
	/**
	 * Método responsável por consultar um Usuario e descriptografar os seus dados
	 *	 
	 * @param {@link chavePublica} - Chave publica 
	 * 
	 * @author arthurmapati@gmail.com
	 * 
	 * @return {@link UsuarioSaveDTO} - Retorno do Usuario Cadastrado
	 *	 
	 */
	
	public UsuarioSaveDTO consultarUsuarioPorChave(String chavePublica) {
		ChaveUsuarioDTO chaveUsuarioDTO = recuperarChaveUsuario(chavePublica);
		
		if(chaveUsuarioDTO == null) {
			throw new NotFoundException(MensagemConstante.CHAVE_INVALIDA + chavePublica);
		}
		
		Optional<Usuario> usuario = dao.findById(chaveUsuarioDTO.getId());

		if (!usuario.isPresent()) {
			throw new NotFoundException(MensagemConstante.USUARIO_NAO_ENCONTRADO);
		}
		
		KeyPair parChaves = mapChave.get(chaveUsuarioDTO);
		securityRSA = new SecurityConfigRSA(parChaves.getPublic(), parChaves.getPrivate());

		try {
			
			byte[] chaveDescriptografada = securityRSA.descriptografar(chavePublica.trim());
			securityAES = new SecurityConfigAES(chaveDescriptografada);			
			
			String nomeDescriptografado = securityAES.descriptografar(usuario.get().getNome());
			String emailDescriptografado = securityAES.descriptografar(usuario.get().getEmail());
			
			usuario.get().setNome(nomeDescriptografado);
			usuario.get().setEmail(emailDescriptografado);
		
		} catch (InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
			throw new NegocioException(MensagemConstante.ERRO_NA_CRIPTOGRAFIA + e);
		} catch (NoSuchAlgorithmException e) {
			throw new NegocioException(MensagemConstante.ERRO_AO_GERAR_CHAVE + e);
		}

		return UsuarioSaveDTO.mapper(usuario.get());
	}
	
	/**
	 * Método responsável por consultar um Usuario
	 *
	 * @param {@link Long id} - Id do Usuario
	 * 
	 * @author arthurmapati@gmail.com	 
	 * 
	 * @return {@link UsuarioSaveDTO} - Retorno do Usuario Cadastrado
	 *	 
	 */
	
	public UsuarioSaveDTO consultarUsuarioPorId(Long id) {
		Optional<Usuario> resultado = dao.findById(id);

		if (!resultado.isPresent()) {
			throw new NotFoundException(MensagemConstante.USUARIO_NAO_ENCONTRADO + id);
		}
		
		return UsuarioSaveDTO.mapper(resultado.get());
	}

	public static Map<ChaveUsuarioDTO, KeyPair> getMapchave() {
		return mapChave;
	}

}
