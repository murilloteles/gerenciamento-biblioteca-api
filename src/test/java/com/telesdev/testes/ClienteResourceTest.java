package com.telesdev.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.telesdev.biblioteca.domain.Cliente;
import com.telesdev.biblioteca.domain.Endereco;
import com.telesdev.biblioteca.exceptions.CampoInvalidoException;
import com.telesdev.biblioteca.exceptions.ClienteNaoEncontradoException;
import com.telesdev.biblioteca.repository.ClienteRepository;
import com.telesdev.biblioteca.repository.EnderecoRepository;
import com.telesdev.biblioteca.service.ClienteService;
import com.telesdev.biblioteca.service.EnderecoService;

@ExtendWith(MockitoExtension.class)
public class ClienteResourceTest {
    
    @Mock
    private ClienteRepository clienteRepositoryMock;
    
    @Mock
    private EnderecoRepository enderecoRepositoryMock;
    
    @Mock
    private EnderecoService enderecoServiceMock;
    
    
    @InjectMocks
    private ClienteService clienteService;


    @Test
    public void deveLancarExcecaoQuandoBuscarClienteNaoExistente() {
    	Optional<Cliente> optionalCliente = Optional.empty();

        when(clienteRepositoryMock.findById(0L)).thenReturn(optionalCliente);

        assertThrows(ClienteNaoEncontradoException.class,() -> clienteService.buscar(0L));
    }
    
    @Test
    public void deveRetornarClienteQuandoBuscar() {
    	
    	Cliente clientePopulado = getClientePopulado();
    	Optional<Cliente> optionalCliente = Optional.of(clientePopulado);

        when(clienteRepositoryMock.findById(1L)).thenReturn(optionalCliente);

        assertEquals(clientePopulado,clienteService.buscar(1L));
    }
    
    
    @Test
    public void deveLancarExcecaoQuandoForAtualizarClienteNaoExistente() {
    	Cliente cliente = new Cliente();
    	cliente.setId(0L);
    	Optional<Cliente> optionalCliente = Optional.empty();

        when(clienteRepositoryMock.findById(0L)).thenReturn(optionalCliente);

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.atualizar(cliente));
    }
    
    @Test
    public void deveAlterarDadosQuandoAtualizarCliente() {
      	Cliente clientePopulado = getClientePopulado();
    	Optional<Cliente> clienteReturn = Optional.of(clientePopulado);

    	Cliente clienteUpdate = clientePopulado;
    	
    	clienteUpdate.setNome("Murillo Teles");
    	clienteUpdate.getEndereco().setBairro("Asa Norte");
    	clienteUpdate.getEndereco().setComplemento("Quadra 16");
    	clienteUpdate.getEndereco().setNumero(20L);

        when(clienteRepositoryMock.findById(clientePopulado.getId())).thenReturn(clienteReturn);
        when(enderecoServiceMock.getEnderecoPopulado(clienteUpdate.getEndereco(), true)).thenReturn(clienteUpdate.getEndereco());
        when(clienteRepositoryMock.save(clienteUpdate)).thenReturn(clienteUpdate);

        assertEquals(clienteUpdate, clienteService.atualizar(clienteUpdate));
    }
    
    @Test
    public void deveSalvarCliente() {
      	Cliente clientePopulado = getClientePopulado();

        when(enderecoServiceMock.getEnderecoPopulado(clientePopulado.getEndereco(), false)).thenReturn(clientePopulado.getEndereco());
        when(clienteRepositoryMock.save(clientePopulado)).thenReturn(clientePopulado);

        assertEquals(clientePopulado,clienteService.salvar(clientePopulado, false));
    }
    
    @Test
    public void deveLancarExcecaoQuandoSalvarClienteEnderecoIncompleto() {
      	Cliente cliente = new Cliente();
      	Endereco endereco = new Endereco();
    	endereco.setBairro("Asa Norte");
    	endereco.setComplemento("Quadra 16");
    	endereco.setNumero(20L);
    	cliente.setEndereco(endereco);

        when(enderecoServiceMock.getEnderecoPopulado(cliente.getEndereco(), false)).thenThrow(CampoInvalidoException.class);

        assertThrows(CampoInvalidoException.class, () -> clienteService.salvar(cliente, false));
    }
    

	private Cliente getClientePopulado() {
		Cliente cliente = new Cliente();
		cliente.setId(1L);
		cliente.setNome("Murillo");
		cliente.setNascimento(new Date());
		cliente.setNacionalidade("Brasileiro");
		cliente.setDocumento("123.456.789-03");
		cliente.setDataCadastro(new Date());
		
		Endereco endereco = new Endereco();
		endereco.setId(1L);
		endereco.setCep("70830-901");
		endereco.setLogradouro("SGAN 601 Lote 1");
		endereco.setLocalidade("Brasilia");
		endereco.setUf("Df");

		cliente.setEndereco(endereco);


    	return cliente;
	} 
}