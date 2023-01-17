package com.telesdev.biblioteca.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.telesdev.biblioteca.domain.DetalhesErro;
import com.telesdev.biblioteca.exceptions.CampoInvalidoException;
import com.telesdev.biblioteca.exceptions.ClienteNaoEncontradoException;
import com.telesdev.biblioteca.exceptions.EnderecoNaoEncontradoException;
import com.telesdev.biblioteca.exceptions.FuncionarioNaoEncontradoException;

@ControllerAdvice
public class ResourcesExceptionHandler {
	
	@ExceptionHandler(ClienteNaoEncontradoException.class)
	public ResponseEntity<DetalhesErro>  handlerClienteNaoEncontradoException(ClienteNaoEncontradoException e,
																						HttpServletRequest request) {
		
		DetalhesErro detalhesErro = new DetalhesErro();
		detalhesErro.setTitulo(e.getMessage());
		detalhesErro.setStatus(404);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detalhesErro);
		
	}
	
	@ExceptionHandler(FuncionarioNaoEncontradoException.class)
	public ResponseEntity<DetalhesErro>  handlerFuncionarioNaoEncontradoException(FuncionarioNaoEncontradoException e,
																						HttpServletRequest request) {
		
		DetalhesErro detalhesErro = new DetalhesErro();
		detalhesErro.setTitulo(e.getMessage());
		detalhesErro.setStatus(404);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detalhesErro);
		
	}
	
	@ExceptionHandler(EnderecoNaoEncontradoException.class)
	public ResponseEntity<DetalhesErro>  handlerEnderecoNaoEncontradoException(EnderecoNaoEncontradoException e,
																						HttpServletRequest request) {
		
		DetalhesErro detalhesErro = new DetalhesErro();
		detalhesErro.setTitulo(e.getMessage());
		detalhesErro.setStatus(404);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detalhesErro);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<DetalhesErro> handleMethodArgumentNotValidException
							(MethodArgumentNotValidException e, HttpServletRequest request) {
		
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(400);
		
		erro.setTitulo("Requisição inválida. " + e.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(CampoInvalidoException.class)
	public ResponseEntity<DetalhesErro> handleCampoInvalidoExceptionException
							(CampoInvalidoException e, HttpServletRequest request) {
		
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(400);
		
		erro.setTitulo("Requisição inválida. " + e.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<DetalhesErro> handleDataIntegrityViolationException
							(DataIntegrityViolationException e, HttpServletRequest request) {
		
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(400);
		erro.setTitulo("Requisição inválida");
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
}
