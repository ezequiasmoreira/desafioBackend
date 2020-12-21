package com.ezequias.desafio.spec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezequias.desafio.domain.Usuario;
import com.ezequias.desafio.dto.UsuarioDTO;
import com.ezequias.desafio.repository.UsuarioRepository;
import com.ezequias.desafio.exception.DataIntegrityException;


@Service
public class UsuarioSpec {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public void validarSeEmailJaEstaCadastrado(UsuarioDTO dto) {		
		Usuario usuario = usuarioRepository.findByEmail(dto.getEmail()); 
		
		if(usuario != null) {
			throw new DataIntegrityException("Email já cadastrado");
		}		
		
	}
	
public void validarSeExisteEmail(String email) {		
		if (email == "") {
			throw new DataIntegrityException("Email não informado");
		}			
	}
	

}
