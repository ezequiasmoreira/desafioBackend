package com.ezequias.desafio.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ezequias.desafio.domain.Cliente;
import com.ezequias.desafio.domain.Endereco;
import com.ezequias.desafio.domain.Telefone;
import com.ezequias.desafio.dto.ClienteDTO;
import com.ezequias.desafio.dto.EnderecoDTO;
import com.ezequias.desafio.dto.TelefoneDTO;
import com.ezequias.desafio.exception.DataIntegrityException;
import com.ezequias.desafio.exception.ObjectNotFoundException;
import com.ezequias.desafio.repository.ClienteRepository;
import com.ezequias.desafio.spec.ClienteSpec;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoService enderecoService;
	@Autowired
	private TelefoneService telefoneService;
	@Autowired
	private ClienteSpec clienteSpec;
	
	public Cliente salvar(Cliente cliente) {
		cliente.setId(null);
		return clienteRepository.save(cliente);
	}
	
	public Cliente atualizar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public Cliente paraDTO(ClienteDTO dto) {
		Boolean existeEnderecoPrincipal = false;
		clienteSpec.validarCliente(dto);
		
		Cliente cliente = new Cliente();
		cliente.setCpf(dto.getCpf());
		cliente.setNome(dto.getNome());		
		
		for (EnderecoDTO enderecoDTO : dto.getEnderecos()) {
			Endereco enderecosSecundario = enderecoService.paraDTO(enderecoDTO);	
			enderecosSecundario = enderecoService.salvar(enderecosSecundario);
			adicionarEndereco(cliente, enderecosSecundario);	
			if (enderecoDTO.getPrincipal()==null) {
				throw new DataIntegrityException("Endereço principal não informado");
			}
			if (enderecoDTO.getPrincipal().equals("principal")) {
				existeEnderecoPrincipal = true;
				cliente.setEndereco(enderecosSecundario);	
			}
		}
		
		if (!existeEnderecoPrincipal) {
			throw new DataIntegrityException("Endereço principal não informado");
		}
		
		for (TelefoneDTO telefoneDTO : dto.getTelefones()) {
			Telefone telefone = telefoneService.paraDTO(telefoneDTO);
			adicionarTelefone(cliente, telefoneService.salvar(telefone));
		}
		
		return cliente;
	}
	
	public Cliente obterPorId(Integer clienteId) throws ObjectNotFoundException {		
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		return cliente.orElseThrow(()-> new ObjectNotFoundException("Cliente não encontrado."));
	}
	
	public List<Cliente> obterTodos() throws ObjectNotFoundException {		
		return clienteRepository.findAll();
	}
	
	public void excluir(Cliente cliente) throws DataIntegrityException {	
		try {
			clienteRepository.deleteById(cliente.getId());
		}
		catch (DataIntegrityViolationException exception) {
			throw new DataIntegrityException("Não é possível excluir o cliente");
		}
	}
	
	public void excluirTelefone(Telefone telefone, Cliente cliente) throws DataIntegrityException {	
		try {			
			cliente.getTelefones().remove(telefone);
			clienteRepository.save(cliente);
			telefoneService.excluir(telefone);
		}
		catch (DataIntegrityViolationException exception) {
			throw new DataIntegrityException("Não é possível excluir o telefone: " +telefone.toString());
		}
	}
	
	public void excluirEndereco(Endereco endereco, Cliente cliente) throws DataIntegrityException {	
		clienteSpec.validarSePermiteExcluirEndereco(endereco, cliente);
		try {			
			cliente.getEnderecos().remove(endereco);
			clienteRepository.save(cliente);
			enderecoService.excluir(endereco);
		}
		catch (DataIntegrityViolationException exception) {
			throw new DataIntegrityException("Não é possível excluir o endereço");
		}
	}
	
	public Cliente adicionarEndereco(Cliente cliente, Endereco endereco)  {		
		cliente.getEnderecos().addAll(Arrays.asList(endereco));	
		return cliente;
	}
	
	public Cliente adicionarTelefone(Cliente cliente, Telefone telefone)  {		
		cliente.getTelefones().addAll(Arrays.asList(telefone));	
		return cliente;
	}
	
	public Cliente atualizarCliente(ClienteDTO clienteDTO)  {
		Boolean existeEnderecoPrincipal = false;
		
		if (clienteDTO.getId() == null) {
			throw new DataIntegrityException("Não é possível atualizar o cliente");
		}
		
		Cliente cliente = obterPorId(clienteDTO.getId());
		
		cliente.setNome(clienteDTO.getNome() == null ? cliente.getNome() : clienteDTO.getNome());
		cliente.setEndereco(clienteDTO.getEnderecoId() == null ? cliente.getEndereco() : enderecoService.obterPorId(clienteDTO.getEnderecoId()));
		if (clienteDTO.getCpf() != null) {
			clienteSpec.validarCliente(clienteDTO, cliente);
			cliente.setCpf(clienteDTO.getCpf());
		}
		
		for (EnderecoDTO enderecoDTO : clienteDTO.getEnderecos()) {
			Endereco enderecosSecundario = enderecoService.obterPorId(enderecoDTO.getId());			
			if (enderecoDTO.getPrincipal()==null) {
				throw new DataIntegrityException("Endereço principal não informado");
			}
			if (enderecoDTO.getPrincipal().equals("principal")) {
				existeEnderecoPrincipal = true;
				cliente.setEndereco(enderecosSecundario);	
			}
		}
		
		if (!existeEnderecoPrincipal) {
			throw new DataIntegrityException("Endereço principal não informado");
		}		
		
		return atualizar(cliente);
	}
}
