package com.telesdev.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telesdev.biblioteca.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}