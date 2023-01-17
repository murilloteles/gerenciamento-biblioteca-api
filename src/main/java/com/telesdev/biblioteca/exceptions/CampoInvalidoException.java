package com.telesdev.biblioteca.exceptions;

public class CampoInvalidoException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CampoInvalidoException(String mensagem) {
		super(mensagem);
	}

}
