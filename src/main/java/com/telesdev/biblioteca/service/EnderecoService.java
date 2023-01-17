package com.telesdev.biblioteca.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.telesdev.biblioteca.domain.Endereco;
import com.telesdev.biblioteca.exceptions.CampoInvalidoException;
import com.telesdev.biblioteca.exceptions.EnderecoNaoEncontradoException;
import com.telesdev.biblioteca.repository.EnderecoRepository;

@Service
public class EnderecoService {
	@Autowired
	EnderecoRepository enderecoRepository;

	public Endereco buscar(Long id) {
		Optional<Endereco> optionalEndereco = enderecoRepository.findById(id);

		if (!optionalEndereco.isPresent())
			throw new EnderecoNaoEncontradoException("O Endereco ID " + id + " não foi encontrado");

		return optionalEndereco.get();

	}
	
	public Endereco buscarCepMaiorIncidenciaFuncionario() {
		Endereco retorno = null;
		List<String> listGroupByCepFuncionarios = enderecoRepository.findGroupByCepFuncionarios();
		if(listGroupByCepFuncionarios != null) {
			retorno = buscar(listGroupByCepFuncionarios.get(0));
		}else {
			retorno = new Endereco();
		}
		return retorno;
	}
	
	public Endereco getEnderecoPopulado(Endereco enderecoBase, boolean isUpdate) {
		validarCamposEndereco(enderecoBase);
		
		Endereco endereco = null;
		boolean isNovoEndereco = false;
		
		if(enderecoBase.getId()!= null) {
			endereco = buscar(enderecoBase.getId());				
		}else {
			 isNovoEndereco = true;
			 endereco = buscar(enderecoBase.getCep());
		}
		
		if(isUpdate || isNovoEndereco)
			 endereco = atualizarEndereco(endereco,enderecoBase);
		
		return endereco;
	}
	
	private Endereco buscar(String cep) {
		RestTemplate restTemplate = new RestTemplate();
		String uriViaCep = "https://viacep.com.br/ws/"+ cep + "/json"; 

		RequestEntity<Void> request = RequestEntity.get(URI.create(uriViaCep)).build();

		ResponseEntity<Endereco> response = restTemplate.exchange(request, Endereco.class);
		
		Endereco enderecoResponse = response.getBody();
		return enderecoResponse;
	}
	
	private Endereco atualizarEndereco(Endereco enderecoBase,Endereco enderecoRequisicao) {
		enderecoBase.setNumero(enderecoRequisicao.getNumero());
		
		if(isCampoPreenchido(enderecoRequisicao.getLogradouro())) 
			enderecoBase.setLogradouro(enderecoRequisicao.getLogradouro());
		
		if(isCampoPreenchido(enderecoRequisicao.getComplemento())) 
			enderecoBase.setComplemento(enderecoRequisicao.getComplemento());
		
		if(isCampoPreenchido(enderecoRequisicao.getBairro())) 
			enderecoBase.setBairro(enderecoRequisicao.getBairro());
		
		return enderecoBase;

	}

	private boolean isCampoPreenchido(String campo) {
		return campo != null && !campo.isEmpty();
	}

	private void validarCamposEndereco(Endereco endereco) {
		if( endereco != null && (endereco.getId() != null || endereco.getCep() != null))
			return;
		
		throw new CampoInvalidoException("Campo Endereco inválido.");
	}

}
