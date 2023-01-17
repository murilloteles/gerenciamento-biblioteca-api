package com.telesdev.biblioteca.exceptions;

public class FuncionarioNaoEncontradoException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FuncionarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

}
