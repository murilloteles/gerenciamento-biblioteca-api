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

import com.telesdev.biblioteca.domain.Endereco;
import com.telesdev.biblioteca.domain.Funcionario;
import com.telesdev.biblioteca.exceptions.CampoInvalidoException;
import com.telesdev.biblioteca.exceptions.FuncionarioNaoEncontradoException;
import com.telesdev.biblioteca.repository.EnderecoRepository;
import com.telesdev.biblioteca.repository.FuncionarioRepository;
import com.telesdev.biblioteca.service.EnderecoService;
import com.telesdev.biblioteca.service.FuncionarioService;

@ExtendWith(MockitoExtension.class)
public class FuncionarioResourceTest {
    
    @Mock
    private FuncionarioRepository funcionarioRepositoryMock;
    
    @Mock
    private EnderecoRepository enderecoRepositoryMock;
    
    @Mock
    private EnderecoService enderecoServiceMock;
    
    
    @InjectMocks
    private FuncionarioService funcionarioService;


    @Test
    public void deveLancarExcecaoQuandoBuscarFuncionarioNaoExistente() {
    	Optional<Funcionario> optionalFuncionario = Optional.empty();

        when(funcionarioRepositoryMock.findById(0L)).thenReturn(optionalFuncionario);

        assertThrows(FuncionarioNaoEncontradoException.class,() -> funcionarioService.buscar(0L));
    }
    
    @Test
    public void deveRetornarFuncionarioQuandoBuscar() {
    	
    	Funcionario funcionarioPopulado = getFuncionarioPopulado();
    	Optional<Funcionario> optionalFuncionario = Optional.of(funcionarioPopulado);

        when(funcionarioRepositoryMock.findById(1L)).thenReturn(optionalFuncionario);

        assertEquals(funcionarioPopulado,funcionarioService.buscar(1L));
    }
    
    
    @Test
    public void deveLancarExcecaoQuandoForAtualizarFuncionarioNaoExistente() {
    	Funcionario funcionario = new Funcionario();
    	funcionario.setId(0L);
    	Optional<Funcionario> optionalFuncionario = Optional.empty();

        when(funcionarioRepositoryMock.findById(0L)).thenReturn(optionalFuncionario);

        assertThrows(FuncionarioNaoEncontradoException.class, () -> funcionarioService.atualizar(funcionario));
    }
    
    @Test
    public void deveAlterarDadosQuandoAtualizarFuncionario() {
      	Funcionario funcionarioPopulado = getFuncionarioPopulado();
    	Optional<Funcionario> funcionarioReturn = Optional.of(funcionarioPopulado);

    	Funcionario funcionarioUpdate = funcionarioPopulado;
    	
    	funcionarioUpdate.setNome("Murillo Teles");
    	funcionarioUpdate.getEndereco().setBairro("Asa Norte");
    	funcionarioUpdate.getEndereco().setComplemento("Quadra 16");
    	funcionarioUpdate.getEndereco().setNumero(20L);

        when(funcionarioRepositoryMock.findById(funcionarioPopulado.getId())).thenReturn(funcionarioReturn);
        when(enderecoServiceMock.getEnderecoPopulado(funcionarioUpdate.getEndereco(), true)).thenReturn(funcionarioUpdate.getEndereco());
        when(funcionarioRepositoryMock.save(funcionarioUpdate)).thenReturn(funcionarioUpdate);

        assertEquals(funcionarioUpdate, funcionarioService.atualizar(funcionarioUpdate));
    }
    
    @Test
    public void deveSalvarFuncionario() {
      	Funcionario funcionarioPopulado = getFuncionarioPopulado();

        when(enderecoServiceMock.getEnderecoPopulado(funcionarioPopulado.getEndereco(), false)).thenReturn(funcionarioPopulado.getEndereco());
        when(funcionarioRepositoryMock.save(funcionarioPopulado)).thenReturn(funcionarioPopulado);

        assertEquals(funcionarioPopulado,funcionarioService.salvar(funcionarioPopulado, false));
    }
    
    @Test
    public void deveLancarExcecaoQuandoSalvarFuncionarioEnderecoIncompleto() {
      	Funcionario funcionario = new Funcionario();
      	Endereco endereco = new Endereco();
    	endereco.setBairro("Asa Norte");
    	endereco.setComplemento("Quadra 16");
    	endereco.setNumero(20L);
    	funcionario.setEndereco(endereco);

        when(enderecoServiceMock.getEnderecoPopulado(funcionario.getEndereco(), false)).thenThrow(CampoInvalidoException.class);

        assertThrows(CampoInvalidoException.class, () -> funcionarioService.salvar(funcionario, false));
    }
    
    @Test
    public void deveRetornarEnderecoDoCepQuandoBuscarCepMaiorIncidencia() {
    	Endereco endereco = getFuncionarioPopulado().getEndereco();
    	
        when(enderecoServiceMock.buscarCepMaiorIncidenciaFuncionario()).thenReturn(endereco);

        assertEquals(endereco,funcionarioService.buscarCepMaiorIncidencia());
    }
    

	private Funcionario getFuncionarioPopulado() {
		Funcionario funcionario = new Funcionario();
		funcionario.setId(1L);
		funcionario.setNome("Murillo");
		funcionario.setNascimento(new Date());
		funcionario.setNacionalidade("Brasileiro");
		funcionario.setDocumento("123.456.789-03");
		funcionario.setDataContratacao(new Date());
		
		Endereco endereco = new Endereco();
		endereco.setId(1L);
		endereco.setCep("70830-901");
		endereco.setLogradouro("SGAN 601 Lote 1");
		endereco.setLocalidade("Brasilia");
		endereco.setUf("Df");

		funcionario.setEndereco(endereco);


    	return funcionario;
	} 
}