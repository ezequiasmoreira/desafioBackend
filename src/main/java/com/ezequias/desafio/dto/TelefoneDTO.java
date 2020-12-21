package com.ezequias.desafio.dto;

public class TelefoneDTO {
	private Integer id;	
	private Integer dd;
	private Integer ddd;
	private String telefone;
	private Integer entidadeId;
	
	public TelefoneDTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDd() {
		return dd;
	}

	public void setDd(Integer dd) {
		this.dd = dd;
	}

	public Integer getDdd() {
		return ddd;
	}

	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Integer getEntidadeId() {
		return entidadeId;
	}

	public void setEntidadeId(Integer entidadeId) {
		this.entidadeId = entidadeId;
	}

}
