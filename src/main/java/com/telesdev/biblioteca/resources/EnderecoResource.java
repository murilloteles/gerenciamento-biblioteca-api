package com.telesdev.biblioteca.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telesdev.biblioteca.domain.Endereco;
import com.telesdev.biblioteca.domain.VariaveisGlobais;
import com.telesdev.biblioteca.service.EnderecoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping(VariaveisGlobais.URN_BASE + "/endereco")
@Api(tags="Endereço Resource")
@Tag(name = "Endereço Resource", description = "Resource para buscar Endereços cadastrados.")
public class EnderecoResource{
	
	@Autowired
	EnderecoService enderecoService;
	
	
    @ApiOperation(value = "Retorna Todos os Endereços cadastrados")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Sucesso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção")
	})
	@GetMapping(produces="application/json")
	public ResponseEntity<Page<Endereco>> listar(@RequestParam(
											            value = "pagina",
											            required = false,
											            defaultValue = "0") int pagina,
											    @RequestParam(
											            value = "tamanho",
											            required = false,
											            defaultValue = "10") int tamanho) {
		return ResponseEntity.status(HttpStatus.OK).body(enderecoService.listar(pagina,tamanho));
	}
	
    @ApiOperation(value = "Retorna o Endereço com CEP indicado")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Sucesso"),
		    @ApiResponse(code = 400, message = "Requisição inválida"),
		    @ApiResponse(code = 404, message = "Não Encontrado"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção")
	})
	@GetMapping(value="/{cep}", produces="application/json")
	public ResponseEntity<?> buscar(@PathVariable("cep") String cep) {
		return ResponseEntity.status(HttpStatus.OK).body(enderecoService.buscar(cep));
	}
}
