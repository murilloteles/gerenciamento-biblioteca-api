package com.telesdev.biblioteca.repository;

import java.util.List;

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
}
