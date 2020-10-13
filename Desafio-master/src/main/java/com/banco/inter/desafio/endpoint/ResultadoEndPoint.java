package com.banco.inter.desafio.endpoint;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.inter.desafio.dto.ResultadoDTO;
import com.banco.inter.desafio.service.ResultadoService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "resultados")
public class ResultadoEndPoint {
	
	@Autowired
	private ResultadoService resultadoService;
	
	@ApiOperation(value = "Recuperar o digito único de um número" , nickname = "recuperarDigitoUnico", response = Long.class)
	@GetMapping("/digito-unico")
	public ResponseEntity<Long> recuperarDigitoUnico(@RequestBody @Valid ResultadoDTO resultadoDTO) {	
		Long resultado = resultadoService.digitoUnico(resultadoDTO);
		return ResponseEntity.ok(resultado);	
	}
	
	@ApiOperation(value = "Recuperar todos os resultados realizado" , nickname = "recuperarTodos", response = ResultadoDTO.class)
	@GetMapping()
	public ResponseEntity<List<ResultadoDTO>> recuperarTodos() {
		List<ResultadoDTO> resultados = resultadoService.findALL();
		return ResponseEntity.ok(resultados);
	}
}
