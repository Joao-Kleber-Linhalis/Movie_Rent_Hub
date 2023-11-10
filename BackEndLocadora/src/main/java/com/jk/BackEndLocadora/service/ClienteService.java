package com.jk.BackEndLocadora.service;

import com.jk.BackEndLocadora.domain.Classe;
import com.jk.BackEndLocadora.domain.Cliente;
import com.jk.BackEndLocadora.domain.dto.ClasseDTO;
import com.jk.BackEndLocadora.domain.dto.ClienteDTO;
import com.jk.BackEndLocadora.exceptions.ObjectNotFoundException;
import com.jk.BackEndLocadora.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    ModelMapper modelMapper = new ModelMapper();

    public ClienteDTO findById(Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return converterOptionalClienteParaDTO(cliente).orElseThrow(() ->new ObjectNotFoundException(findByIdDisable(id) + " Id: " + id));
    }

    public List<ClienteDTO> findAllByStatus(Boolean status){
        List<Cliente> clienteList = clienteRepository.findByStatus(status);
        return clienteList.stream().map(cliente -> modelMapper.map(cliente,ClienteDTO.class)).collect(Collectors.toList());
    }

    public ClienteDTO create(ClienteDTO clienteDTO){
        clienteDTO.setId(null);
        if(clienteRepository.findByCpf(clienteDTO.getCpf()).isPresent()){
            throw new DataIntegrityViolationException("CPF já cadastrado");
        }
        return modelMapper.map(clienteRepository.save(modelMapper.map(clienteDTO,Cliente.class)), ClienteDTO.class);
    }

    public ClienteDTO update(Long id, ClienteDTO clienteDTO){
        clienteDTO.setId(id);
        ClienteDTO clienteDTO1 = findById(id);
        if(clienteDTO1.getDependentes() != null && !clienteDTO1.getDependentes().isEmpty()){
            clienteDTO.setDependentes(clienteDTO1.getDependentes());
        }
        if(!clienteDTO.getAtivo() && clienteDTO1.getAtivo()){
            return disable(id);
        }
        else{
            return modelMapper.map(clienteRepository.save(modelMapper.map(clienteDTO,Cliente.class)), ClienteDTO.class);
        }
    }

    public ClienteDTO disable(Long id){
        Cliente cliente = modelMapper.map(findById(id), Cliente.class);
        List<String> nomes = clienteRepository.findActiveDependentesByClienteId(id);
        if(!nomes.isEmpty()){
            String nomesDependentes = String.join(", ", nomes);
            throw new DataIntegrityViolationException("Cliente possui " + nomes.size() + " dependentes ativos associados a ele. Nomes:  " + nomesDependentes);
        }
        cliente.setAtivo(false);
        return modelMapper.map(clienteRepository.save(cliente), ClienteDTO.class);
    }

    public ClienteDTO able(Long id){
        Cliente cliente = modelMapper.map(findById(id), Cliente.class);
        cliente.setAtivo(true);
        return modelMapper.map(clienteRepository.save(cliente), ClienteDTO.class);
    }

    private Optional<ClienteDTO> converterOptionalClienteParaDTO(Optional<Cliente> optionalCliente) {
        return optionalCliente.map(cliente -> modelMapper.map(cliente, ClienteDTO.class));
    }

    private Optional<Cliente> converterOptionalDTOparaCliente(Optional<ClienteDTO> optionalCliente) {
        return optionalCliente.map(clienteDTO -> modelMapper.map(clienteDTO, Cliente.class));
    }

    private String findByIdDisable(Long id) {
        Optional<Cliente> Cliente = clienteRepository.findByIdAndAtivo(id,false);
        if (Cliente.isPresent()){
            return "Cliente atualmente desativado";
        }
        else {
            return "Cliente não encontrado";
        }
    }
}
