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


@RestController
@RequestMapping(VariaveisGlobais.URN_BASE + "/endereco")
public class EnderecoResource{
	
	@Autowired
	EnderecoService enderecoService;
	
	@GetMapping
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
	
	@GetMapping("/{cep}")
	public ResponseEntity<?> buscar(@PathVariable("cep") String cep) {
		return ResponseEntity.status(HttpStatus.OK).body(enderecoService.buscar(cep));
	}
}
