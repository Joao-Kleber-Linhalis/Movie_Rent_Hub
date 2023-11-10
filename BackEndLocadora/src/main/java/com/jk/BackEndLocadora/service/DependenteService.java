package com.jk.BackEndLocadora.service;

import com.jk.BackEndLocadora.domain.Cliente;
import com.jk.BackEndLocadora.domain.Dependente;
import com.jk.BackEndLocadora.domain.dto.ClienteDTO;
import com.jk.BackEndLocadora.domain.dto.DependenteDTO;
import com.jk.BackEndLocadora.exceptions.MaxDependentesAtivosExceededException;
import com.jk.BackEndLocadora.exceptions.ObjectNotFoundException;
import com.jk.BackEndLocadora.repository.DependenteRepository;
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
public class DependenteService {

    @Autowired
    private DependenteRepository dependenteRepository;

    ModelMapper modelMapper = new ModelMapper();

    public DependenteDTO findById(Long id){
        Optional<Dependente> dependente = dependenteRepository.findById(id);
        return converterOptionalDependenteParaDTO(dependente).orElseThrow(() ->new ObjectNotFoundException(findByIdDisable(id) + " Id: " + id));

    }

    public List<DependenteDTO> findAllByStatus(Boolean status){
        List<Dependente> dependenteList = dependenteRepository.findByStatus(status);
        return dependenteList.stream().map(dependente -> modelMapper.map(dependente,DependenteDTO.class)).collect(Collectors.toList());
    }

    public DependenteDTO create(DependenteDTO dependenteDTO){
        dependenteDTO.setId(null);
        if(dependenteRepository.findByCpf(dependenteDTO.getCpf()).isPresent()){
            throw new DataIntegrityViolationException("CPF já cadastrado");
        }
        validarMaxDependentesAtivos(dependenteDTO.getCliente().getId());
        return modelMapper.map(dependenteRepository.save(modelMapper.map(dependenteDTO, Dependente.class)), DependenteDTO.class);
    }

    public DependenteDTO update(Long id, DependenteDTO dependenteDTO){
        dependenteDTO.setId(id);
        DependenteDTO dependenteDTO1 = findById(id);
        if (dependenteDTO.getCliente().getId() != dependenteDTO1.getCliente().getId()) {
            validarMaxDependentesAtivos(dependenteDTO.getCliente().getId());
        }
        return modelMapper.map(dependenteRepository.save(modelMapper.map(dependenteDTO, Dependente.class)), DependenteDTO.class);
    }


    public DependenteDTO disable(Long id){
        Dependente dependente = modelMapper.map(findById(id), Dependente.class);
        dependente.setAtivo(false);
        return modelMapper.map(dependenteRepository.save(dependente), DependenteDTO.class);
    }

    public DependenteDTO able(Long id){
        Dependente dependente = modelMapper.map(findById(id), Dependente.class);
        validarMaxDependentesAtivos(dependente.getCliente().getId());
        dependente.setAtivo(true);
        return modelMapper.map(dependenteRepository.save(dependente), DependenteDTO.class);
    }

    private void validarMaxDependentesAtivos(Long clienteId) {
        List<String> nomes = NomesDependentesAtivosPorClienteId(clienteId);
        if (nomes.size() >= 3) {
            String nomesDependentes = String.join(", ", nomes);
            throw new MaxDependentesAtivosExceededException("Cliente já possui 3 dependentes ativos cadastrados. Nomes:  " + nomesDependentes);
        }
    }

    private List<String> NomesDependentesAtivosPorClienteId(Long clienteId) {
        return dependenteRepository.findNomesDependentesAtivosPorClienteId(clienteId);
    }
    private Optional<DependenteDTO> converterOptionalDependenteParaDTO(Optional<Dependente> optionalDependente) {
        return optionalDependente.map(dependente -> modelMapper.map(dependente, DependenteDTO.class));
    }

    private Optional<Dependente> converterOptionalDTOParaDependente(Optional<DependenteDTO> optionalDependente) {
        return optionalDependente.map(dependenteDTO -> modelMapper.map(dependenteDTO, Dependente.class));
    }

    private String findByIdDisable(Long id) {
        Optional<Dependente> Dependente = dependenteRepository.findByIdAndAtivo(id,false);
        if (Dependente.isPresent()){
            return "Dependente atualmente desativado";
        }
        else {
            return "Dependente não encontrado";
        }
    }

}
