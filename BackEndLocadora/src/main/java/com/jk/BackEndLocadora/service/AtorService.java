package com.jk.BackEndLocadora.service;

import com.jk.BackEndLocadora.domain.Ator;
import com.jk.BackEndLocadora.domain.dto.AtorDTO;
import com.jk.BackEndLocadora.exceptions.ObjectNotFoundException;
import com.jk.BackEndLocadora.repository.AtorRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AtorService {

    @Autowired
    private AtorRepository atorRepository;

    ModelMapper modelMapper = new ModelMapper();


    public AtorDTO findById(Long id) {
        Optional<Ator> ator = atorRepository.findById(id);
        return converterOptionalAtorParaDTO(ator).orElseThrow(() -> new ObjectNotFoundException(findByIdDisable(id) + " Id: " + id));
    }

    public List<AtorDTO> findAllByStatus(Boolean status) {
        List<Ator> atorList = atorRepository.findByStatus(status);
        return atorList.stream().map(ator -> modelMapper.map(ator,AtorDTO.class)).collect(Collectors.toList());
    }

    public AtorDTO create(AtorDTO atorDTO){
        atorDTO.setId(null);
        Optional<Ator> ator = atorRepository.findByNome(atorDTO.getNome());
        if (ator.isPresent()){
            String status = ator.get().getAtivo() ? "Ativo" : "Inativo";
            throw new IllegalArgumentException("Nome de Ator já cadastrado, Id: " + ator.get().getId() + ",Status: " + status);
        }
        //Ator ator = atorRepository.save(modelMapper.map(atorDTO,Ator.class));
        return modelMapper.map(atorRepository.save(modelMapper.map(atorDTO,Ator.class)),AtorDTO.class);
    }



    public AtorDTO update(Long id, AtorDTO atorDTO) {
        atorDTO.setId(id);
        AtorDTO atorDTO1 = findById(id);
        if(!atorDTO.getAtivo() && atorDTO1.getAtivo()){
            return disable(id);
        }else {
            return modelMapper.map(atorRepository.save(modelMapper.map(atorDTO,Ator.class)),AtorDTO.class);
        }
    }

    public AtorDTO disable(Long id){
        Ator ator = modelMapper.map(findById(id),Ator.class);
        List<String> titulos = atorRepository.findTitulosByAtorId(id);
        if(!titulos.isEmpty()){
            String nomesTitulos = String.join(", ", titulos);
            throw new DataIntegrityViolationException("Ator possui " + titulos.size() + " filmes associados a ele. Nomes:  " + nomesTitulos);
        }
        ator.setAtivo(false);
        return modelMapper.map(atorRepository.save(ator),AtorDTO.class);
    }

    public AtorDTO able(Long id) {
        Ator ator = modelMapper.map(findById(id),Ator.class);
        ator.setAtivo(true);
        return modelMapper.map(atorRepository.save(ator),AtorDTO.class);
    }

    private Optional<AtorDTO> converterOptionalAtorParaDTO(Optional<Ator> optionalAtor) {
        return optionalAtor.map(ator -> modelMapper.map(ator, AtorDTO.class));
    }

    private Optional<Ator> converterOptionalDTOparaAtor(Optional<AtorDTO> optionalAtorDTO) {
        return optionalAtorDTO.map(atorDTO -> modelMapper.map(atorDTO, Ator.class));
    }

    private String findByIdDisable(Long id) {
        Optional<Ator> ator = atorRepository.findByIdAndAtivo(id,false);
        Optional<AtorDTO> atorDTO = converterOptionalAtorParaDTO(ator);
        if (atorDTO.isPresent()){
            return "Objeto atualmente desativado";
        }
        else {
            return "Objeto não encontrado";
        }
    }


}
