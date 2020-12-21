package com.ezequias.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezequias.desafio.domain.Telefone;
@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Integer>{
}
