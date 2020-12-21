package com.ezequias.desafio.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ezequias.desafio.domain.Endereco;
import com.ezequias.desafio.dto.EnderecoDTO;
import com.ezequias.desafio.exception.DataIntegrityException;
import com.ezequias.desafio.exception.ObjectNotFoundException;
import com.ezequias.desafio.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Endereco obterPorId(Integer enderecoId) throws ObjectNotFoundException {		
		Optional<Endereco> endereco = enderecoRepository.findById(enderecoId);
		return endereco.orElseThrow(()-> new ObjectNotFoundException("Endereço não encontrado."));
	}
	
	public Endereco salvar(Endereco obj) {
		obj.setId(null);
		return enderecoRepository.save(obj);
	}
	
	public Endereco paraDTO(EnderecoDTO dto) {
		Endereco endereco = new Endereco();
		endereco.setCep(dto.getCep());
		endereco.setLogradouro(dto.getLogradouro());
		endereco.setNumero(dto.getNumero());		
		return endereco;
	}
	
	public void excluir(Endereco endereco) throws DataIntegrityException {	
		try {
			enderecoRepository.deleteById(endereco.getId());
		}
		catch (DataIntegrityViolationException exception) {
			throw new DataIntegrityException("Não é possível excluir o endereço");
		}
	}
		
}
