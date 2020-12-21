package com.ezequias.desafio.spec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezequias.desafio.domain.Cliente;
import com.ezequias.desafio.domain.Endereco;
import com.ezequias.desafio.dto.ClienteDTO;
import com.ezequias.desafio.exception.DataIntegrityException;
import com.ezequias.desafio.repository.ClienteRepository;

@Service
public class ClienteSpec {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Boolean validarCliente(ClienteDTO dto) {
		
		if ((dto.getId() != null) || (dto.getCpf() == "")) {
			return true;
		}			
		
		Cliente cliente = clienteRepository.findByCpf(dto.getCpf()); 
		
		if(cliente == null) {
			return true;
		}		
		throw new DataIntegrityException("Cpf já existente");
	}
	
	public Boolean validarCliente(ClienteDTO dto,Cliente cliente) {
		
		Cliente clienteCpf = clienteRepository.findByCpf(dto.getCpf()); 
		if (clienteCpf == null) {
			return true;
		}
		
		if (clienteCpf.equals(cliente)) {
			return true;
		}
		
		throw new DataIntegrityException("Cpf já existente");
	}
	
	public void validarSePermiteExcluirEndereco (Endereco endereco, Cliente cliente) {
		
		if (endereco.equals(cliente.getEndereco())) {			
			throw new DataIntegrityException("Não é possivel excluir o endereço principal, para excluir altere o endereço.");				
		}
		
	}
	

}
