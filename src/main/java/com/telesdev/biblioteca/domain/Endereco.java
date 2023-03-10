package com.telesdev.biblioteca.domain;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(value = "Endereco")
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonInclude(Include.NON_NULL)
    @ApiModelProperty(value = "Preencher somente quando quiser utilizar um Endereço já salvo")
	private Long id;
	
	@Pattern(regexp="^(([0-9]{8})|([0-9]{5}-[0-9]{3}))$",message="Cep com formato inválido. Formatos aceitos: 00000000 ou 00000-000 ")
    @NotEmpty(message = "O campo cep não pode ser vazio.")
    @ApiModelProperty(value = "Preencher para um novo endereço, a partir desse campo é preenchido o restante.")
	private String cep;
	
	@JsonInclude(Include.NON_NULL)
    @ApiModelProperty(value = "Preencher esse campo caso queira mudar o padrão buscado pelo CEP.")
	private String logradouro;
	
	@JsonInclude(Include.NON_NULL)
    @ApiModelProperty(value = "Preencher esse campo caso queira mudar o padrão buscado pelo CEP.")
	private String complemento;
	
	@JsonInclude(Include.NON_NULL)
    @ApiModelProperty(value = "Preencher esse campo caso queira mudar o padrão buscado pelo CEP.")
	private String bairro;
	
	@JsonInclude(Include.NON_NULL)
    @ApiModelProperty(value = "Número da casa/Apartamento.")
	private Long numero;
	
	private String localidade;
	
	private String uf;
	
	@OneToMany(mappedBy = "endereco", cascade= {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH})
	@JsonIgnore
	private List<Pessoa> pessoas;
	
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getLocalidade() {
		return localidade;
	}
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(bairro, cep, complemento, id, localidade, logradouro, numero, pessoas, uf);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		return Objects.equals(bairro, other.bairro) && Objects.equals(cep, other.cep)
				&& Objects.equals(complemento, other.complemento) && Objects.equals(id, other.id)
				&& Objects.equals(localidade, other.localidade) && Objects.equals(logradouro, other.logradouro)
				&& Objects.equals(numero, other.numero) && Objects.equals(pessoas, other.pessoas)
				&& Objects.equals(uf, other.uf);
	}
	@Override
	public String toString() {
		return "Endereco [cep=" + cep + ", logradouro=" + logradouro + ", complemento=" + complemento + ", bairro="
				+ bairro + ", numero=" + numero + ", localidade=" + localidade + ", uf=" + uf + "]";
	}

}
