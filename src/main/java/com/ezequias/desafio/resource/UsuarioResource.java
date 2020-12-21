package com.ezequias.desafio.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.ezequias.desafio.domain.Usuario;
import com.ezequias.desafio.dto.UsuarioDTO;
import com.ezequias.desafio.exception.DataIntegrityException;
import com.ezequias.desafio.repository.UsuarioRepository;
import com.ezequias.desafio.service.UsuarioService;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping("/usuario")
public class UsuarioResource {
	
	@Autowired
	UsuarioService service;
	@Autowired
	UsuarioRepository usuarioRepository;
		
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Usuario> salvar(@RequestBody UsuarioDTO dto) {
		Usuario usuario = service.paraDTO(dto);
		usuario = service.salvar(usuario);
		return ResponseEntity.ok().body(usuario);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Usuario> obterPorId(@PathVariable Integer id) throws ObjectNotFoundException {
		Usuario usuario = service.obterPorId(id);
		return ResponseEntity.ok().body(usuario);
	}	
}
