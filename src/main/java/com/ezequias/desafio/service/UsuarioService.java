package com.ezequias.desafio.service;

import java.util.Optional;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezequias.desafio.domain.Usuario;
import com.ezequias.desafio.dto.UsuarioDTO;
import com.ezequias.desafio.repository.UsuarioRepository;
import com.ezequias.desafio.spec.UsuarioSpec;


@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private UsuarioSpec usuarioSpec;
	
	public Usuario salvar(Usuario usuario) {
		usuario.setId(null);
		return usuarioRepository.save(usuario);
	}
	
	public Usuario paraDTO(UsuarioDTO dto) {
		usuarioSpec.validarSeExisteEmail(dto.getEmail());
		usuarioSpec.validarSeEmailJaEstaCadastrado(dto);
		
		Usuario usuario = new Usuario();
		usuario.setEmail(dto.getEmail());
		usuario.setNome(dto.getNome());
		usuario.setSenha(dto.getSenha());	
		return usuario;
	}
	
	public Usuario obterPorId(Integer usuarioId) throws ObjectNotFoundException {		
		Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
		return usuario.orElseThrow(()-> new ObjectNotFoundException("Usuario não encontrado."));
	}
	
}
