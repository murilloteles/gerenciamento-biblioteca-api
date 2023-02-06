package com.telesdev.biblioteca.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.telesdev.biblioteca.domain.Endereco;
import com.telesdev.biblioteca.domain.Funcionario;
import com.telesdev.biblioteca.domain.VariaveisGlobais;
import com.telesdev.biblioteca.service.FuncionarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping(VariaveisGlobais.URN_BASE + "/funcionarios")
@Api(tags="Funcionarios Resource")
@Tag(name = "Funcionarios Resource", description = "Resource com operações para gerenciar Funcionários")
public class FuncionariosResource{
	
	@Autowired
	FuncionarioService funcionarioService;
	
    @ApiOperation(value = "Retorna Todos os Funcionários cadastrados")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Sucesso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção")
	})
	@GetMapping(produces="application/json")
	public ResponseEntity<Page<Funcionario>> listar(@RequestParam(
												            value = "pagina",
												            required = false,
												            defaultValue = "0") int pagina,
												    @RequestParam(
												            value = "tamanho",
												            required = false,
												            defaultValue = "10") int tamanho) {
    	
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.listar(pagina,tamanho));
	}
	
	
    @ApiOperation(value = "Retorna o Funcionário com ID indicado")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Sucesso"),
		    @ApiResponse(code = 400, message = "Requisição inválida"),
		    @ApiResponse(code = 404, message = "Não Encontrado"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção")
	})
	@GetMapping(value="/{id}",produces="application/json")
	public ResponseEntity<Funcionario> buscar(@PathVariable("id") Long id) {
    	
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.buscar(id));
	}
    
    @ApiOperation(value = "Retorna o Funcionário com Nome indicado")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Sucesso"),
		    @ApiResponse(code = 400, message = "Requisição inválida"),
		    @ApiResponse(code = 404, message = "Não Encontrado"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção")
	})
	@GetMapping(value="/buscar",produces="application/json")
	public ResponseEntity<Funcionario> buscar(@RequestParam(value = "nome",
									            required = true) String nome) {
    	
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.buscar(nome));
	}
    
    @ApiOperation(value = "Salva um Funcionário.")
	@ApiResponses(value = {
		    @ApiResponse(code = 201, message = "Criado com sucesso. No Header Location terá a Url para busca-lo."),
		    @ApiResponse(code = 400, message = "Requisição inválida"),
		    @ApiResponse(code = 404, message = "Não Encontrado"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção")
	})
	@PostMapping(produces="application/json", consumes="application/json")
	public ResponseEntity<?> salvar(@Valid @RequestBody Funcionario funcionario) {
    	
		funcionario = funcionarioService.salvar(funcionario,false);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}").buildAndExpand(funcionario.getId())
						.toUri();
		return ResponseEntity.created(uri).build();
	}
	
    @ApiOperation(value = "Atualiza dados do Funcionário com o ID específico.")
	@ApiResponses(value = {
		    @ApiResponse(code = 204, message = "Sucesso"),
		    @ApiResponse(code = 400, message = "Requisição inválida"),
		    @ApiResponse(code = 404, message = "Não Encontrado"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção")
	})
	@PutMapping(value="/{id}", produces="application/json", consumes="application/json")
	public ResponseEntity<?> atualizar(@PathVariable("id") Long id,
						   				  @Valid @RequestBody Funcionario funcionario) {
		
			funcionario.setId(id);
			funcionarioService.atualizar(funcionario);	
			return ResponseEntity.noContent().build();
	}
	
    @ApiOperation(value = "Exclui o Funcionário com o ID específico.")
	@ApiResponses(value = {
		    @ApiResponse(code = 204, message = "Sucesso"),
		    @ApiResponse(code = 400, message = "Requisição inválida"),
		    @ApiResponse(code = 404, message = "Não Encontrado"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção")
	})
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
    	
			funcionarioService.deletar(id);
			return ResponseEntity.noContent().build();
	}
	
    @ApiOperation(value = "Busca o CEP com maior incidência nos endereços entre os Funcionarios.")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Sucesso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção")
	})
	@GetMapping(value="/cep-maior-incidencia", produces="application/json")
	public ResponseEntity<Endereco> buscarCepMaiorIncidencia() {
    	
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.buscarCepMaiorIncidencia());
	}
	
}
