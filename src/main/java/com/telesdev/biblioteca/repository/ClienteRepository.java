package com.telesdev.biblioteca.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.telesdev.biblioteca.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	   @Query(value = "SELECT c FROM Cliente c ")
	   Page<Cliente> listarClientes(Pageable pageable);

}