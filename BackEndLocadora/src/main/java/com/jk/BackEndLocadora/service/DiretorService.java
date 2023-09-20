package com.jk.BackEndLocadora.service;

import com.jk.BackEndLocadora.domain.Diretor;
import com.jk.BackEndLocadora.domain.dto.AtorDTO;
import com.jk.BackEndLocadora.domain.dto.DiretorDTO;
import com.jk.BackEndLocadora.exceptions.ObjectNotFoundException;
import com.jk.BackEndLocadora.repository.DiretorRepository;
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
public class DiretorService {

    @Autowired
    private DiretorRepository diretorRepository;

    ModelMapper modelMapper = new ModelMapper();

    public DiretorDTO findById(Long id) {
        Optional<Diretor> diretor = diretorRepository.findById(id);
        return converterOptionalDiretorParaDTO(diretor).orElseThrow(() -> new ObjectNotFoundException(findByIdDisable(id) + " Id:" + id));
    }

    public List<DiretorDTO> findAllByStatus(Boolean status) {
        List<Diretor> diretorList = diretorRepository.findByStatus(status);
        return diretorList.stream().map(diretor -> modelMapper.map(diretor, DiretorDTO.class)).collect(Collectors.toList());
    }

    public DiretorDTO create(DiretorDTO diretorDTO) {
        diretorDTO.setId(null);
        if (diretorRepository.findByNome(diretorDTO.getNome()).isPresent()) {
            throw new IllegalArgumentException("Nome de Diretor já cadastrado");
        }
        return modelMapper.map(diretorRepository.save(modelMapper.map(diretorDTO, Diretor.class)), DiretorDTO.class);
    }

    public DiretorDTO update(Long id, DiretorDTO diretorDTO) {
        diretorDTO.setId(id);
        DiretorDTO diretorDTO1 = findById(id);
        if (!diretorDTO.getAtivo() && diretorDTO1.getAtivo()) {
            return disable(id);
        } else {
            return modelMapper.map(diretorRepository.save(modelMapper.map(diretorDTO, Diretor.class)), DiretorDTO.class);
        }


    }

    public DiretorDTO disable(Long id) {
        Diretor diretor = modelMapper.map(findById(id), Diretor.class);
        List<String> titulos = diretorRepository.findTitulosByDiretorId(id);
        if (!titulos.isEmpty()) {
            String nomesTitulos = String.join(", ", titulos);
            throw new DataIntegrityViolationException("Diretor possui " + titulos.size() + " filmes associados a ele. Nomes:  " + nomesTitulos);
        }
        diretor.setAtivo(false);
        return modelMapper.map(diretorRepository.save(diretor), DiretorDTO.class);
    }

    public DiretorDTO able(Long id) {
        Diretor diretor = modelMapper.map(findById(id), Diretor.class);
        diretor.setAtivo(true);
        return modelMapper.map(diretorRepository.save(diretor), DiretorDTO.class);
    }



    private Optional<DiretorDTO> converterOptionalDiretorParaDTO(Optional<Diretor> optionalDiretor) {
        return optionalDiretor.map(diretor -> modelMapper.map(diretor, DiretorDTO.class));
    }

    private Optional<Diretor> converterOptionalDTOparaDiretor(Optional<DiretorDTO> optionalDiretorDTO) {
        return optionalDiretorDTO.map(diretorDTO -> modelMapper.map(diretorDTO, Diretor.class));
    }

    private String findByIdDisable(Long id) {
        Optional<Diretor> diretor = diretorRepository.findByIdAndAtivo(id, false);
        Optional<DiretorDTO> diretorDTO = converterOptionalDiretorParaDTO(diretor);
        if (diretorDTO.isPresent()) {
            return "Objeto atualmente desativado";
        } else {
            return "Objeto não encontrado";
        }
    }

}
