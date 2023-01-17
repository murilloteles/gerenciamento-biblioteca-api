package com.telesdev.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.telesdev.biblioteca.domain.Endereco;
import com.telesdev.biblioteca.exceptions.EnderecoNaoEncontradoException;
import com.telesdev.biblioteca.repository.EnderecoRepository;
import com.telesdev.biblioteca.service.EnderecoService;

@ExtendWith(MockitoExtension.class)
public class EnderecoResourceTest {
    
    @Mock
    private EnderecoRepository enderecoRepositoryMock;
    
    @InjectMocks
    private EnderecoService enderecoService;

    @Test
    public void deveLancarExcecaoQuandoBuscarEnderecoNaoExistente() {
    	Optional<Endereco> optionalEndereco = Optional.empty();

        when(enderecoRepositoryMock.findByCep("70830-000")).thenReturn(optionalEndereco);

        assertThrows(EnderecoNaoEncontradoException.class,() -> enderecoService.buscar("70830-000"));
    }
    
    @Test
    public void deveRetornarEnderecoQuandoBuscar() {
    	
    	Endereco enderecoPopulado = getEnderecoPopulado();
    	Optional<Endereco> optionalEndereco = Optional.of(enderecoPopulado);

        when(enderecoRepositoryMock.findByCep("70830-901")).thenReturn(optionalEndereco);

        assertEquals(enderecoPopulado,enderecoService.buscar("70830-901"));
    }
    
	private Endereco getEnderecoPopulado() {
		Endereco endereco = new Endereco();
		endereco.setId(1L);
		endereco.setCep("70830-901");
		endereco.setLogradouro("SGAN 601 Lote 1");
		endereco.setLocalidade("Brasilia");
		endereco.setUf("Df");

    	return endereco;
	} 
}