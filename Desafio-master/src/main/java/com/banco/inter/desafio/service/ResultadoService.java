package com.banco.inter.desafio.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.inter.desafio.dto.ResultadoDTO;
import com.banco.inter.desafio.dto.UsuarioSaveDTO;

@Service
public class ResultadoService {
	
	@Autowired
	private UsuarioService usuarioService;
	
	private static final int MAX = 10;
	
	private static final Map<Long, ResultadoDTO> mapResultado = new HashMap<Long, ResultadoDTO>();
	
	/**
	 * Método responsável por recuperar o Digito Unico de um Numero
	 *
	 * @param {@link resultadoDTO} - DTO do Resultado
	 * 
	 * @author arthurmapati@gmail.com	 	  
	 * 
	 * @return {@link Long} - Digito unico do numero.
	 *	 
	 */
	
	public Long digitoUnico(ResultadoDTO resultadoDTO) {		
			
		Long valor = valorConcatenado(resultadoDTO.getNumero(), resultadoDTO.getMultiplicador());	
		resultadoDTO.setValorBase(valor);
		
		if(mapResultado.containsKey(valor)) {
			valor = mapResultado.get(valor).getDigitoUnico();
		}else {			
			while(valor >= 10) {
				valor = calcularDigitoUnico(valor);
			}
			
			resultadoDTO.setDigitoUnico(valor);
			addResultado(resultadoDTO);
		}		
		
		return valor;
	}
	
	/**
	 * Método responsável por armazenar os resultado realizados
	 *
	 * @param {@link resultadoDTO} - DTO do Resultado
	 * 
	 * @author arthurmapati@gmail.com	 	 	
	 *	 
	 */
	
	private void addResultado(ResultadoDTO resultadoDTO) {
		
		if(mapResultado.values().size() < MAX) {
			mapResultado.put(resultadoDTO.getValorBase(), resultadoDTO);
		}else {
			removerResultado();
			mapResultado.put(resultadoDTO.getValorBase(), resultadoDTO);
		}		
	}
	
	/**
	 * Método responsável por remover o resultado	 
	 * 
	 * @author arthurmapati@gmail.com	 	 	
	 *	 
	 */
	
	private void removerResultado() {
		Set<Long> chaves = mapResultado.keySet();

		for(Long v_long : chaves)
		{
			mapResultado.remove(v_long);
			break;
		}
	}
	
	/**
	 * Método responsável por calcular o digito unico, dividindo o valor por 10 e somando o seu resto até encontrar o digito unico.
	 *
	 * @param {@link valor} - Valor que será calculado
	 * 
	 * @author arthurmapati@gmail.com	
	 * 
	 * @return {@link Long} - Digito unico do numero. 	 	
	 *	 
	 */
	
	private Long calcularDigitoUnico(Long valor) {
		Long soma = 0l;
		
		while (valor > 0) {
			soma += (valor % 10);			
			valor /= 10;			
		}

		return soma;
	}
	
	/**
	 * Método responsável por listar todos os resultados	 
	 * 
	 * @author arthurmapati@gmail.com	
	 * 
	 * @return {@link List<ResultadoDTO>} - Resultados Realizados 	
	 *	 
	 */
	
	public List<ResultadoDTO> findALL() {
		List<ResultadoDTO> listaResultados = mapResultado.values().stream().map(r -> r).collect(Collectors.toList());
		return listaResultados;
	}
	
	/**
	 * Método responsável por listar todos os resultados por Usuario	 
	 * 
	 * @author arthurmapati@gmail.com	
	 * 
	 * @param {@link Long id} - Id do Usuario
	 * 
	 * @return {@link List<ResultadoDTO>} - Resultados Realizados 	
	 *	 
	 */
	
	public List<ResultadoDTO> recuperarResultadoPorUsuario(Long id) {
		UsuarioSaveDTO usuarioSaveDTO = usuarioService.consultarUsuarioPorId(id);
		
		List<ResultadoDTO> listaResultados = mapResultado.values()
				.stream().filter(resultado -> resultado.getUsuarioDTO()!= null && resultado.getUsuarioDTO().getId().equals(usuarioSaveDTO.getId()))  
                .collect(Collectors.toList()); 
		return listaResultados;
	}
	
	/**
	 * Método responsável por concatenar o valor	 
	 * 
	 * @author arthurmapati@gmail.com	
	 * 
	 * @param {@link String n} - Numero 
	 * @param {@link String k} - Multiplicador
	 * 
	 * @return {@link Long} - Valor que será usado para recuperar o digito único. 	
	 *	 
	 */
	
	private Long valorConcatenado(String n, String k) {
		
		String resultado = "";
		Integer multiplicador = Integer.parseInt(k);
		
		for(int i = 0; i < multiplicador; i++) {
			resultado = resultado + n;			
		}
		
		Long valor = Long.parseLong(resultado.toString());

		return valor;
		
	}

}

