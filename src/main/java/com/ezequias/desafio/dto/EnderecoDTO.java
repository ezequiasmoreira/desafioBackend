package com.ezequias.desafio.dto;

public class EnderecoDTO {
	private Integer id;	
	private String cep;
	private String numero;
	private String principal;
	private String logradouro;
	private Integer entidadeId;
	
	public EnderecoDTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Integer getEntidadeId() {
		return entidadeId;
	}

	public void setEntidadeId(Integer entidadeId) {
		this.entidadeId = entidadeId;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	
}
