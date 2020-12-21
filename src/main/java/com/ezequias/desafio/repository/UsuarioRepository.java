package com.ezequias.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ezequias.desafio.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	@Transactional(readOnly=true)
	Usuario findByEmail(String email);
	
	@Transactional(readOnly=true)
	Usuario findByEmailAndSenha(String email,String senha);
}
