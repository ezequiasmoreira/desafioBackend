package com.ezequias.desafio.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ezequias.desafio.domain.Telefone;
import com.ezequias.desafio.dto.TelefoneDTO;
import com.ezequias.desafio.exception.DataIntegrityException;
import com.ezequias.desafio.exception.ObjectNotFoundException;
import com.ezequias.desafio.repository.TelefoneRepository;

@Service
public class TelefoneService {

	@Autowired
	private TelefoneRepository telefoneRepository;
	
	public Telefone obterPorId(Integer telefoneId) throws ObjectNotFoundException {		
		Optional<Telefone> telefone = telefoneRepository.findById(telefoneId);
		return telefone.orElseThrow(()-> new ObjectNotFoundException("Telefone não encontrado."));
	}
	
	public Telefone salvar(Telefone obj) {
		obj.setId(null);
		return telefoneRepository.save(obj);
	}
	
	public Telefone paraDTO(TelefoneDTO dto) {
		Telefone telefone = new Telefone(null,dto.getDd(),dto.getDdd(),dto.getTelefone());
		return telefone;
	}
	
	public void excluir(Telefone telefone) throws DataIntegrityException {	
		try {
			telefoneRepository.deleteById(telefone.getId());
		}
		catch (DataIntegrityViolationException exception) {
			throw new DataIntegrityException("Não é possível excluir o telefone");
		}
	}
		
}
