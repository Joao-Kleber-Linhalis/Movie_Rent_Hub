package com.jk.BackEndLocadora.service;

import com.jk.BackEndLocadora.domain.Classe;
import com.jk.BackEndLocadora.domain.Classe;
import com.jk.BackEndLocadora.domain.dto.AtorDTO;
import com.jk.BackEndLocadora.domain.dto.ClasseDTO;
import com.jk.BackEndLocadora.domain.dto.ClasseDTO;
import com.jk.BackEndLocadora.exceptions.ObjectNotFoundException;
import com.jk.BackEndLocadora.repository.ClasseRepository;
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
public class ClasseService {

    @Autowired
    private ClasseRepository classeRepository;

    ModelMapper modelMapper = new ModelMapper();

    public ClasseDTO findById(Long id){
        Optional<Classe> classe = classeRepository.findById(id);
        return converterOptionalClasseParaDTO(classe).orElseThrow(() ->new ObjectNotFoundException(findByIdDisable(id) + " Id: " + id));
    }

    public List<ClasseDTO> findAllByStatus(Boolean status){
        List<Classe> classeList = classeRepository.findByStatus(status);
        return classeList.stream().map(classe -> modelMapper.map(classe, ClasseDTO.class)).collect(Collectors.toList());
    }

    public ClasseDTO create(ClasseDTO classeDTO){
        classeDTO.setId(null);
        if(classeRepository.findByNome(classeDTO.getNome()).isPresent()){
            throw new IllegalArgumentException("Nome de Classe já cadastrado");
        }
        return modelMapper.map(classeRepository.save(modelMapper.map(classeDTO,Classe.class)), ClasseDTO.class);
    }

    public ClasseDTO update(Long id, ClasseDTO classeDTO){
        classeDTO.setId(id);
        ClasseDTO classeDTO1 = findById(id);
        if (!classeDTO.getAtivo() && classeDTO1.getAtivo()){
            return disable(id);
        }
        else {
            return modelMapper.map(classeRepository.save(modelMapper.map(classeDTO,Classe.class)), ClasseDTO.class);
        }
    }

    public ClasseDTO disable(Long id){
        Classe classe = modelMapper.map(findById(id),Classe.class);
        List<String> titulos = classeRepository.findTitulosByClasseId(id);
        if(!titulos.isEmpty()){
            String nomesTitulos = String.join(", ", titulos);
            throw new DataIntegrityViolationException("Classe possui " + titulos.size() + " filmes associados a ele. Nomes:  " + nomesTitulos);
        }
        classe.setAtivo(false);
        return modelMapper.map(classeRepository.save(classe), ClasseDTO.class);
    }

    public ClasseDTO able(Long id) {
        Classe classe = modelMapper.map(findById(id),Classe.class);
        classe.setAtivo(true);
        return modelMapper.map(classeRepository.save(classe), ClasseDTO.class);
    }


    private Optional<ClasseDTO> converterOptionalClasseParaDTO(Optional<Classe> optionalClasse) {
        return optionalClasse.map(classe -> modelMapper.map(classe, ClasseDTO.class));
    }

    private Optional<Classe> converterOptionalDTOparaClasse(Optional<ClasseDTO> optionalClasse) {
        return optionalClasse.map(classeDTO -> modelMapper.map(classeDTO, Classe.class));
    }

    private String findByIdDisable(Long id) {
        Optional<Classe> Classe = classeRepository.findByIdAndAtivo(id,false);
        if (Classe.isPresent()){
            return "Classe atualmente desativado";
        }
        else {
            return "Classe não encontrado";
        }
    }


}
