package com.telesdev.biblioteca.exceptions;

public class EnderecoNaoEncontradoException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EnderecoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

}
