package com.ezequias.desafio.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ezequias.desafio.domain.Cliente;
import com.ezequias.desafio.domain.Endereco;
import com.ezequias.desafio.domain.Telefone;
import com.ezequias.desafio.dto.ClienteDTO;
import com.ezequias.desafio.dto.EnderecoDTO;
import com.ezequias.desafio.dto.TelefoneDTO;
import com.ezequias.desafio.service.ClienteService;
import com.ezequias.desafio.service.EnderecoService;
import com.ezequias.desafio.service.TelefoneService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping("/cliente")
public class ClienteResource {
	
	@Autowired
	ClienteService service;
	@Autowired
	EnderecoService enderecoService;
	@Autowired
	TelefoneService telefoneService;
	
	@Transactional(rollbackOn = Exception.class)
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Cliente> salvar(@RequestBody ClienteDTO dto) {
		Cliente cliente = service.paraDTO(dto);
		cliente = service.salvar(cliente);
		return ResponseEntity.ok().body(cliente);
	}
	
	@RequestMapping(value="add-endereco",method=RequestMethod.POST)
	public ResponseEntity<Endereco> adicionarEndereco(@RequestBody EnderecoDTO enderecoDTO) throws ObjectNotFoundException {
		Endereco endereco = enderecoService.paraDTO(enderecoDTO);
		endereco = enderecoService.salvar(endereco);
		Cliente cliente = service.obterPorId(enderecoDTO.getEntidadeId());
		cliente = service.adicionarEndereco(cliente,endereco);
		service.atualizar(cliente);
		return ResponseEntity.ok().body(endereco);
	}
	
	@RequestMapping(value="add-telefone",method=RequestMethod.POST)
	public ResponseEntity<Telefone> adicionarTelefone(@RequestBody TelefoneDTO telefoneDTO) throws ObjectNotFoundException {
		Telefone telefone = telefoneService.paraDTO(telefoneDTO);
		telefone = telefoneService.salvar(telefone);
		Cliente cliente = service.obterPorId(telefoneDTO.getEntidadeId());
		cliente = service.adicionarTelefone(cliente,telefone);
		service.atualizar(cliente);
		return ResponseEntity.ok().body(telefone);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> obterPorId(@PathVariable Integer id) throws ObjectNotFoundException {
		Cliente cliente = service.obterPorId(id);
		return ResponseEntity.ok().body(cliente);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Cliente>> findAll() {
		List<Cliente> clientes = service.obterTodos();  
		return ResponseEntity.ok().body(clientes);
	}
	
	@Transactional(rollbackOn = Exception.class)
	@RequestMapping(value="excluir-telefone/{telefoneId}/cliente/{clienteId}", method=RequestMethod.DELETE)
	public ResponseEntity<Integer> excluirTelefone(@PathVariable Integer telefoneId,@PathVariable Integer clienteId) throws ObjectNotFoundException {
		Telefone telefone = telefoneService.obterPorId(telefoneId);
		Cliente cliente = service.obterPorId(clienteId);
		service.excluirTelefone(telefone,cliente);	
		return ResponseEntity.noContent().build();
	}
	
	@Transactional(rollbackOn = Exception.class)
	@RequestMapping(value="excluir-endereco/{enderecoIds}/cliente/{clienteId}", method=RequestMethod.DELETE)
	public ResponseEntity<Integer> excluirEndereco(@PathVariable String enderecoIds,@PathVariable Integer clienteId) throws ObjectNotFoundException {
		String[] arrayDeIds = enderecoIds.split(",");
		for(String enderecoId : arrayDeIds) {
			Endereco endereco = enderecoService.obterPorId(Integer.valueOf(enderecoId));
			Cliente cliente = service.obterPorId(clienteId);
			service.excluirEndereco(endereco,cliente);	
		}
		return ResponseEntity.noContent().build();
	}
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
		Cliente cliente = service.obterPorId(id);
		service.excluir(cliente);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody ClienteDTO clienteDTO) throws ObjectNotFoundException {		
		service.atualizarCliente(clienteDTO);		
		return ResponseEntity.noContent().build();
	}
}
