package com.telesdev.biblioteca.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.telesdev.biblioteca.domain.Cliente;
import com.telesdev.biblioteca.domain.VariaveisGlobais;
import com.telesdev.biblioteca.service.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping(VariaveisGlobais.URN_BASE + "/clientes")
@Api(tags="Clientes Resource")
@Tag(name = "Clientes Resource", description = "Resource com operações para gerenciar Clientes")
public class ClientesResource{
	
	@Autowired
	ClienteService clienteService;
	
    @ApiOperation(value = "Retorna Todos os Clientes cadastrados")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Sucesso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção")
	})
	@GetMapping(produces="application/json")
	public ResponseEntity<Page<Cliente>> listar(@RequestParam(
											            value = "pagina",
											            required = false,
											            defaultValue = "0") int pagina,
											    @RequestParam(
											            value = "tamanho",
											            required = false,
											            defaultValue = "10") int tamanho) {
		return ResponseEntity.status(HttpStatus.OK).body(clienteService.listar(pagina,tamanho));
	}
	
    @ApiOperation(value = "Retorna o Cliente com ID indicado")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Sucesso"),
		    @ApiResponse(code = 400, message = "Requisição inválida"),
		    @ApiResponse(code = 404, message = "Não Encontrado"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção")
	})
	@GetMapping(value="/{id}", produces="application/json")
	public ResponseEntity<Cliente> buscar(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscar(id));
	}
	
    @ApiOperation(value = "Salva um Cliente.")
	@ApiResponses(value = {
		    @ApiResponse(code = 201, message = "Criado com sucesso. No Header Location terá a Url para busca-lo."),
		    @ApiResponse(code = 400, message = "Requisição inválida"),
		    @ApiResponse(code = 404, message = "Não Encontrado"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção")
	})
	@PostMapping(produces="application/json", consumes="application/json")
	public ResponseEntity<?> salvar(@Valid @RequestBody Cliente cliente) {
		cliente = clienteService.salvar(cliente,false);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}").buildAndExpand(cliente.getId())
						.toUri();
		return ResponseEntity.created(uri).build();
	}
	
    @ApiOperation(value = "Atualiza dados do Cliente com o ID específico.")
	@ApiResponses(value = {
		    @ApiResponse(code = 204, message = "Sucesso"),
		    @ApiResponse(code = 400, message = "Requisição inválida"),
		    @ApiResponse(code = 404, message = "Não Encontrado"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção")
	})
	@PutMapping(value="/{id}", produces="application/json", consumes="application/json")
	public ResponseEntity<?> atualizar(@PathVariable("id") Long id,
						   				  @Valid @RequestBody Cliente cliente) {
		
			cliente.setId(id);
			clienteService.atualizar(cliente);	
			return ResponseEntity.noContent().build();
	}
	
	
}
