package com.banco.inter.desafio.desafio.service;

import static org.junit.Assert.assertNotNull;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.banco.inter.desafio.dao.UsuarioDAO;
import com.banco.inter.desafio.dto.ChaveUsuarioDTO;
import com.banco.inter.desafio.entidade.Usuario;
import com.banco.inter.desafio.security.SecurityConfigAES;
import com.banco.inter.desafio.security.SecurityConfigRSA;
import com.banco.inter.desafio.service.UsuarioService;

public class UsuarioServiceTest {
	
	@InjectMocks
	private UsuarioService usuarioService;
	
	@Mock
	private UsuarioDAO dao;
	
	@Mock
	private SecurityConfigAES securityAES;
	
	@Mock
	private SecurityConfigRSA securityRSA;	
	
	private static final Map<ChaveUsuarioDTO, KeyPair> mapChave = new HashMap<>();
	
	private String chavePublica;
	
	private Usuario mockUsuario() {
		Usuario usuario = new Usuario();		
		usuario.setNome("Arthur Mapa");
		usuario.setEmail("arthurmapati@gmail.com");
		return usuario;
	}
	
	private Usuario mockUsuarioSalvo() {
		Usuario usuario = new Usuario();		
		usuario.setId(1l);
		usuario.setNome("YP94rXYSGF4T86Xn1GAzdg==");
		usuario.setEmail("29UAQvF+KHHOal6PDcGAP4WR5HP7QrWBNUhzq/q+8HQ=");
		return usuario;
	}
	
	private Optional<Usuario>  mockUsuarioOptional() {
		Optional<Usuario> optional = Optional.of(mockUsuarioSalvo());
		return optional;
	}	
	
	private void mockChave() throws NoSuchAlgorithmException, InvalidKeyException,	NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		SecretKey secretKey = SecurityConfigAES.getChave();
		ChaveUsuarioDTO chaveUsuarioDTO = new ChaveUsuarioDTO();
		KeyPair parChave = SecurityConfigRSA.getParChave();
		securityRSA = new SecurityConfigRSA(parChave.getPublic(), parChave.getPrivate());
		String chaveCriptografada = securityRSA.criptografar(secretKey.getEncoded());
		chaveUsuarioDTO.setId(1l);
		chaveUsuarioDTO.setChavePublica(chaveCriptografada);
		chavePublica = chaveCriptografada;
		mapChave.put(chaveUsuarioDTO, parChave);
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void salvarTest() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
		Usuario usuario = mockUsuario();		
		Mockito.when( securityAES.criptografar(Mockito.anyString())).thenReturn(Mockito.anyString());
		Mockito.when( dao.save(usuario)).thenReturn(mockUsuarioSalvo());
		assertNotNull(usuarioService.salvar(usuario));
	}
	
	@Test
	public void listarTodosTest() {		
		Mockito.when( dao.findAll()).thenReturn(Arrays.asList(mockUsuarioSalvo()));
		assertNotNull(usuarioService.listarTodos());
	}
	
	@Test
	public void atualizarTest() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
		Usuario usuario = mockUsuarioSalvo();		
		mockChave();
		UsuarioService.getMapchave().putAll(mapChave);
		Mockito.when( dao.save(usuario)).thenReturn(mockUsuarioSalvo());
		assertNotNull(usuarioService.atualiza(usuario));
	}
	

	
	@Test
	public void recuperarChavePorUsuarioTest() throws NoSuchAlgorithmException{
		UsuarioService.getMapchave().putAll(mapChave);
		assertNotNull(usuarioService.recuperarChavePorUsuario(1l));
	}
	
	@Test
	public void removerUsuarioTest(){
		Usuario usuario = mockUsuarioSalvo();		
		usuarioService.removerUsuario(usuario);
	}
	
	@Test
	public void consultarUsuarioPorIdTest(){
		Mockito.when(dao.findById(1l)).thenReturn(mockUsuarioOptional());		
		assertNotNull(usuarioService.consultarUsuarioPorId(1l));
	}

}
