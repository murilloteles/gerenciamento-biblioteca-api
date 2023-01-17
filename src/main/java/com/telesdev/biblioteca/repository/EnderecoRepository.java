package com.telesdev.biblioteca.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.telesdev.biblioteca.domain.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

	@Query(value = "SELECT e.cep" +
     	   	   "  FROM Funcionario f" +
			   "  INNER JOIN f.pessoa p" +
			   "  INNER JOIN p.endereco e" +
		 	   "  GROUP BY e.cep")
	List<String> findGroupByCepFuncionarios();
	

	@Query(value = "SELECT e FROM Endereco e ")
	Page<Endereco> listarEnderecos(PageRequest pageRequest);
	
	Optional<Endereco> findByCep(String cep);

}
