package com.banco.inter.desafio.endpoint;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.inter.desafio.dto.ResultadoDTO;
import com.banco.inter.desafio.dto.UsuarioDTO;
import com.banco.inter.desafio.dto.UsuarioSaveDTO;
import com.banco.inter.desafio.entidade.Usuario;
import com.banco.inter.desafio.service.ResultadoService;
import com.banco.inter.desafio.service.UsuarioService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "usuarios")
public class UsuarioEndPoint {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ResultadoService resultadoService;
	
	@ApiOperation(value = "Salvar novo usuário" , nickname = "savarUsuario", response = UsuarioDTO.class)
	@PostMapping
	public ResponseEntity<UsuarioDTO> save(@RequestBody @Valid UsuarioSaveDTO usuarioDTO){	
		UsuarioDTO uDTO = usuarioService.salvar(UsuarioDTO.mapper(usuarioDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(uDTO);
	}
	
	@ApiOperation(value = "Atualizar um usuário" , nickname = "atualizarUsuario", response = UsuarioDTO.class)
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDTO> atualizar(@PathVariable(name = "id") Long id, @RequestBody @Valid UsuarioSaveDTO usuarioDTO){
		Usuario u = UsuarioDTO.mapper(usuarioDTO);
		u.setId(id);
		UsuarioSaveDTO uDTO = usuarioService.atualiza(u);
		return ResponseEntity.ok(uDTO);
	}
	
	@ApiOperation(value = "Consultar um usuário pelo Chave Publica" , nickname = "consultarUsuario", response = UsuarioDTO.class)
	@GetMapping("/")
	public ResponseEntity<UsuarioDTO> consultarPorId(@RequestBody String chavePublica){	
		UsuarioDTO usuarioDTO = usuarioService.consultarUsuarioPorChave(chavePublica);
		return ResponseEntity.ok(usuarioDTO);		
	}
	
	@ApiOperation(value = "Listar todos os usuários cadastrados" , nickname = "listarTodos", response = UsuarioSaveDTO.class)
	@GetMapping()
	public ResponseEntity<List<UsuarioSaveDTO>> listarTodos(){		
		List<UsuarioSaveDTO> usuariosDTO = usuarioService.listarTodos();
		return ResponseEntity.ok(usuariosDTO);	
	}
	
	@ApiOperation(value = "Remover um usuário" , nickname = "removerUsuario")	
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> remover(@PathVariable("id") Long id) {
    	UsuarioSaveDTO usuarioSaveDTO = usuarioService.consultarUsuarioPorId(id);

        if (usuarioSaveDTO == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        usuarioService.removerUsuario(UsuarioSaveDTO.mapper(usuarioSaveDTO));
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    
	@ApiOperation(value = "Listar os resultados por usuáros" , nickname = "recuperarResultado", response = ResultadoDTO.class)
	@GetMapping("/{id}/resultados")
	public ResponseEntity<List<ResultadoDTO>> recuperarResultadoPorUsuario(@PathVariable(name = "id") Long id){
		List<ResultadoDTO> resultados = resultadoService.recuperarResultadoPorUsuario(id);
		return ResponseEntity.ok(resultados);
	}
	
	@ApiOperation(value = "Recuperar a chave publica do usuário" , nickname = "recuperarChavePublica", response = String.class)	
	@GetMapping("/{id}/chave-publica")
	public ResponseEntity<String> recuperarChavePublica(@PathVariable(name = "id") Long id){		
		String chavePublica = usuarioService.recuperarChavePorUsuario(id);
		return ResponseEntity.ok(chavePublica);	
	}

}
