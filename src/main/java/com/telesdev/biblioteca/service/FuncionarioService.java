package com.telesdev.biblioteca.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.telesdev.biblioteca.domain.Endereco;
import com.telesdev.biblioteca.domain.Funcionario;
import com.telesdev.biblioteca.domain.Pessoa;
import com.telesdev.biblioteca.exceptions.FuncionarioNaoEncontradoException;
import com.telesdev.biblioteca.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@Autowired
	EnderecoService enderecoService;

	public Page<Funcionario> listar(int pagina, int tamanho) {
		 PageRequest pageRequest = PageRequest.of(
			 	 pagina,
			 	 tamanho,
                 Sort.Direction.ASC,
                 "pessoa.nome");
		 return funcionarioRepository.listarFuncionarios(pageRequest);
	}

	public Funcionario salvar(Funcionario funcionario, boolean isUpdate) {
		Endereco enderecoPopulado = enderecoService.getEnderecoPopulado(funcionario.getEndereco(), isUpdate);
		funcionario.setEndereco(enderecoPopulado);
		Pessoa pessoa = funcionario;
		funcionario.setPessoa(pessoa);

		return funcionarioRepository.save(funcionario);
	}

	public Funcionario buscar(Long id) {
		Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
		
		if(!optionalFuncionario.isPresent())
			throw new FuncionarioNaoEncontradoException("O Funcionario ID " + id +" não foi encontrado");
		
		return optionalFuncionario.get();	
		
	}
	
	public void deletar(Long id) {
		try {
			funcionarioRepository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			throw new FuncionarioNaoEncontradoException("O Funcionario ID " + id +" não foi encontrado");
		}
	}

	public void atualizar(Funcionario funcionario) {
		Funcionario funcionarioReturn = verificaSeExisteFuncionario(funcionario.getId());
		atualizarDados(funcionarioReturn, funcionario);
		salvar(funcionarioReturn, true);
	}
	
	private void atualizarDados(Funcionario funcionarioReturn, Funcionario funcionario) {
		funcionarioReturn.setNome(funcionario.getNome());
		funcionarioReturn.setNascimento(funcionario.getNascimento());
		funcionarioReturn.setNacionalidade(funcionario.getNacionalidade());
		funcionarioReturn.setDocumento(funcionario.getDocumento());
		funcionarioReturn.setEndereco(funcionario.getEndereco());
		funcionarioReturn.setDataContratacao(funcionario.getDataContratacao());
	}

	private Funcionario verificaSeExisteFuncionario(Long id) {
		return buscar(id);
	}

	public Endereco buscarCepMaiorIncidencia() {
		return enderecoService.buscarCepMaiorIncidenciaFuncionario();
	}

}
