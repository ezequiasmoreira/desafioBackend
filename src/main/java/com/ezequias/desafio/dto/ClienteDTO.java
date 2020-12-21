package com.ezequias.desafio.dto;

import java.util.ArrayList;
import java.util.List;

import com.ezequias.desafio.domain.Cliente;
public class ClienteDTO {
	private Integer id;		
	private String nome;	
	private String cpf;	
	private Integer enderecoId;
	private EnderecoDTO enderecoDTO;
	private List<EnderecoDTO> enderecosDTO = new ArrayList<>();
	private List<TelefoneDTO> telefonesDTO = new ArrayList<>();
	
	public ClienteDTO() {
	}
	
	public ClienteDTO(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.cpf = cliente.getCpf();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public EnderecoDTO getEndereco() {
		return enderecoDTO;
	}

	public void setEndereco(EnderecoDTO enderecoDTO) {
		this.enderecoDTO = enderecoDTO;
	}

	public List<EnderecoDTO> getEnderecos() {
		return enderecosDTO;
	}

	public void setEnderecos(List<EnderecoDTO> enderecosDTO) {
		this.enderecosDTO = enderecosDTO;
	}

	public List<TelefoneDTO> getTelefones() {
		return telefonesDTO;
	}

	public void setTelefones(List<TelefoneDTO> telefonesDTO) {
		this.telefonesDTO = telefonesDTO;
	}

	public Integer getEnderecoId() {
		return enderecoId;
	}

	public void setEnderecoId(Integer enderecoId) {
		this.enderecoId = enderecoId;
	}
	
}
