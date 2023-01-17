package com.telesdev.biblioteca.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.telesdev.biblioteca.domain.Cliente;
import com.telesdev.biblioteca.domain.VariaveisGlobais;
import com.telesdev.biblioteca.service.ClienteService;


@RestController
@RequestMapping(VariaveisGlobais.URN_BASE + "/clientes")
public class ClientesResource{
	
	@Autowired
	ClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(clienteService.listar());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscar(id));
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@Valid @RequestBody Cliente cliente) {
		cliente = clienteService.salvar(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}").buildAndExpand(cliente.getId())
						.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(@PathVariable("id") Long id,
						   				  @Valid @RequestBody Cliente cliente) {
		
			cliente.setId(id);
			clienteService.atualizar(cliente);	
			return ResponseEntity.noContent().build();
	}
	
	
}
