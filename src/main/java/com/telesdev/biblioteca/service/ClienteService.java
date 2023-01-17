package com.telesdev.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telesdev.biblioteca.domain.Cliente;
import com.telesdev.biblioteca.exceptions.ClienteNaoEncontradoException;
import com.telesdev.biblioteca.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;

	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}

	public Cliente salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public Cliente buscar(Long id) {
		Optional<Cliente> optionalCliente = clienteRepository.findById(id);
		
		if(!optionalCliente.isPresent())
			throw new ClienteNaoEncontradoException("O Cliente ID " + id +" não foi encontrado");
		
		return optionalCliente.get();	
		
	}
	
	public void atualizar(Cliente cliente) {
		Cliente clienteReturn = verificaSeExisteCliente(cliente.getId());
		atualizarDados(clienteReturn, cliente);
		salvar(clienteReturn);
	}
	
	private void atualizarDados(Cliente clienteReturn, Cliente cliente) {
		clienteReturn.setNome(cliente.getNome());
		clienteReturn.setNascimento(cliente.getNascimento());
		clienteReturn.setNacionalidade(cliente.getNacionalidade());
	}

	private Cliente verificaSeExisteCliente(Long id) {
		return buscar(id);
	}

}
