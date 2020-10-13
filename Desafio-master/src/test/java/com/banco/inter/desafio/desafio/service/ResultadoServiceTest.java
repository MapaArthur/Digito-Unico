package com.banco.inter.desafio.desafio.service;

import static org.junit.Assert.assertNotNull;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.banco.inter.desafio.dto.ResultadoDTO;
import com.banco.inter.desafio.dto.UsuarioSaveDTO;
import com.banco.inter.desafio.service.ResultadoService;
import com.banco.inter.desafio.service.UsuarioService;

public class ResultadoServiceTest {
	
	@InjectMocks
	private ResultadoService resultadoService;
	
	@Mock
	private UsuarioService usuarioService;
	
	private ResultadoDTO mockResultado() {
		ResultadoDTO resultado = new ResultadoDTO();
		resultado.setNumero("9875");
		resultado.setMultiplicador("4");
		return resultado;
	}
	
	private ResultadoDTO mockResultadoAcimaDeDez() {		
        Random gerador = new Random();
        int valor = gerador.nextInt(100);
        
        ResultadoDTO resultado = new ResultadoDTO();		
		resultado.setMultiplicador("2");
        resultado.setNumero(String.valueOf(valor));
        
       return resultado;
	}
	
	private UsuarioSaveDTO mockUsuarioDTO() {
		UsuarioSaveDTO usuario = new UsuarioSaveDTO();
		usuario.setId(1l);
		usuario.setEmail("arthur.mapa@gmail.com");
		usuario.setNome("Arthur Mapa");
		return usuario;
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
		
	@Test
	public void digitoUnicoTest() {
		ResultadoDTO resultadoDTO = mockResultado();
		assertNotNull(resultadoService.digitoUnico(resultadoDTO));		
	}
	
	@Test
	public void digitoUnicoJaCalculadoTest() {
		ResultadoDTO resultadoDTO = mockResultado();
		assertNotNull(resultadoService.digitoUnico(resultadoDTO));		
	}
	
	@Test
	public void digitoUnicoEstourarLimiteTest() {
		for(int i = 0; i < 11; i++) {			
			ResultadoDTO resultadoDTO = mockResultadoAcimaDeDez();		
			assertNotNull(resultadoService.digitoUnico(resultadoDTO));		
		}
	}
	
	@Test
	public void findALLTest() {		
		assertNotNull(resultadoService.findALL());		
	}
	
	@Test
	public void recuperarResultadoPorUsuarioTest() {
		Mockito.when(usuarioService.consultarUsuarioPorId(1l)).thenReturn(mockUsuarioDTO());
		assertNotNull(resultadoService.recuperarResultadoPorUsuario(1l));		
	}
	

}
