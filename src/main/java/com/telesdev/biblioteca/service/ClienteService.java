package com.telesdev.biblioteca.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.telesdev.biblioteca.domain.Cliente;
import com.telesdev.biblioteca.domain.Endereco;
import com.telesdev.biblioteca.domain.Pessoa;
import com.telesdev.biblioteca.exceptions.ClienteNaoEncontradoException;
import com.telesdev.biblioteca.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	EnderecoService enderecoService;

	public Page<Cliente> listar(int pagina, int tamanho) {
		 PageRequest pageRequest = PageRequest.of(
				 	 pagina,
				 	 tamanho,
	                 Sort.Direction.ASC,
	                 "pessoa.nome");
        return clienteRepository.listarClientes(pageRequest);
	}

	public Cliente salvar(Cliente cliente, boolean isUpdate) {
		Endereco enderecoPopulado = enderecoService.getEnderecoPopulado(cliente.getEndereco(), isUpdate);
		cliente.setEndereco(enderecoPopulado);
		Pessoa pessoa = cliente;
		cliente.setPessoa(pessoa);
		if(!isUpdate)
			cliente.setDataCadastro(new Date());
		return clienteRepository.save(cliente);
	}

	public Cliente buscar(Long id) {
		Optional<Cliente> optionalCliente = clienteRepository.findById(id);
		
		if(!optionalCliente.isPresent())
			throw new ClienteNaoEncontradoException("O Cliente ID " + id +" não foi encontrado");
		
		return optionalCliente.get();	
		
	}
	
	public Cliente atualizar(Cliente cliente) {
		Cliente clienteReturn = verificaSeExisteCliente(cliente.getId());
		atualizarDados(clienteReturn, cliente);
		return salvar(clienteReturn, true);
	}
	
	private void atualizarDados(Cliente clienteReturn, Cliente cliente) {
		clienteReturn.setNome(cliente.getNome());
		clienteReturn.setNascimento(cliente.getNascimento());
		clienteReturn.setNacionalidade(cliente.getNacionalidade());
		clienteReturn.setDocumento(cliente.getDocumento());
		clienteReturn.setEndereco(cliente.getEndereco());
		clienteReturn.setDataCadastro(cliente.getDataCadastro());
	}

	private Cliente verificaSeExisteCliente(Long id) {
		return buscar(id);
	}

}
