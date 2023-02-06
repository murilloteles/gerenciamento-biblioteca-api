package com.telesdev.biblioteca.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.telesdev.biblioteca.domain.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	@Query(value = "SELECT f FROM Funcionario f ")
	Page<Funcionario> listarFuncionarios(PageRequest pageRequest);

	Optional<Funcionario> findByNomeIgnoreCase(String nome);
}