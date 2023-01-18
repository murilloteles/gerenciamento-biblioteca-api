package com.telesdev.biblioteca.domain;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "DetalheErro")
public class DetalhesErro {
	private String titulo;
	private int status;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}

