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


@RestController
@RequestMapping(VariaveisGlobais.URN_BASE + "/funcionarios")
public class FuncionariosResource{
	
	@Autowired
	FuncionarioService funcionarioService;
	
	@GetMapping
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
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.buscar(id));
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@Valid @RequestBody Funcionario funcionario) {
		funcionario = funcionarioService.salvar(funcionario,false);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}").buildAndExpand(funcionario.getId())
						.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(@PathVariable("id") Long id,
						   				  @Valid @RequestBody Funcionario funcionario) {
		
			funcionario.setId(id);
			funcionarioService.atualizar(funcionario);	
			return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
			funcionarioService.deletar(id);
			return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/cep-maior-incidencia")
	public ResponseEntity<Endereco> buscarCepMaiorIncidencia() {
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.buscarCepMaiorIncidencia());
	}
	
}
