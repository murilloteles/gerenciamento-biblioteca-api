package com.telesdev.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telesdev.biblioteca.domain.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}