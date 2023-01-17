package com.telesdev.biblioteca.domain;

public class CepValidation {
	public static void main(String[] args) {
		System.out.println("Válido: " + validateCpf("73.330029"));
	}
	
	private static boolean validateCpf(String cpf) {
		if(cpf.matches("^(([0-9]{8})|([0-9]{5}-[0-9]{3}))$")){
        	return true;
		}else if (cpf.matches("^(([0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2})|([0-9]{11}))$")) {
        	return true;
        }
        return false;
    }
}
