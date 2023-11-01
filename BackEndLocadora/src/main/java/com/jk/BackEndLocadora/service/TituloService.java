package com.jk.BackEndLocadora.service;

import com.jk.BackEndLocadora.domain.Diretor;
import com.jk.BackEndLocadora.domain.Titulo;
import com.jk.BackEndLocadora.domain.dto.DiretorDTO;
import com.jk.BackEndLocadora.domain.dto.ItemDTO;
import com.jk.BackEndLocadora.domain.dto.TituloDTO;
import com.jk.BackEndLocadora.domain.enums.CategoriaFilme;
import com.jk.BackEndLocadora.exceptions.ObjectNotFoundException;
import com.jk.BackEndLocadora.repository.ItemRepository;
import com.jk.BackEndLocadora.repository.TituloRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TituloService {

    @Autowired
    private TituloRepository tituloRepository;

    @Autowired
    private DiretorService diretorService;

    @Autowired
    private ClasseService classeService;

    @Autowired
    private AtorService atorService;
    ModelMapper modelMapper = new ModelMapper();

    public TituloDTO findById(Long id) {
        Optional<Titulo> titulo = tituloRepository.findById(id);
        return converterOptionalTituloParaDTO(titulo).orElseThrow(() -> new ObjectNotFoundException(findByIdDisable(id) + " Id:" + id));
    }

    public List<TituloDTO> findAllByStatus(Boolean status) {
        List<Titulo> tituloList = tituloRepository.findByStatus(status);
        return tituloList.stream().map(titulo -> modelMapper.map(titulo, TituloDTO.class)).collect(Collectors.toList());
    }

    public TituloDTO create(TituloDTO tituloDTO){
        tituloDTO.setId(null);
        diretorService.findById(tituloDTO.getDiretor().getId());
        classeService.findById(tituloDTO.getClasse().getId());
        if(!tituloDTO.getAtores().isEmpty()){
            tituloDTO.getAtores().forEach(atorDTO -> atorService.findById(atorDTO.getId()));
        }
        return modelMapper.map(tituloRepository.save(modelMapper.map(tituloDTO, Titulo.class)), TituloDTO.class);
    }

    public TituloDTO update(Long id, TituloDTO tituloDTO){
        tituloDTO.setId(id);
        TituloDTO tituloDTO1 = findById(id);

        // Verifique se o DTO do banco (tituloDTO1) possui uma lista de itens não nula e não vazia
        if (tituloDTO1.getItems() != null && !tituloDTO1.getItems().isEmpty()) {
            // Se a lista do DTO do banco não for nula e não estiver vazia,
            // atribua-a ao DTO que está sendo atualizado
            tituloDTO.setItems(tituloDTO1.getItems());
        }

        if (!tituloDTO.getAtivo() && tituloDTO1.getAtivo()) {
            return disable(id);
        }

        return modelMapper.map(tituloRepository.save(modelMapper.map(tituloDTO, Titulo.class)), TituloDTO.class);
    }

    public TituloDTO disable(Long id){
        Titulo titulo = modelMapper.map(findById(id), Titulo.class);
        Long qtdItens = tituloRepository.countItensAtivosPorTituloId(id);
        if(qtdItens > 0){
           throw new DataIntegrityViolationException("Título possui " + qtdItens + " ativos realizados a ele!");
        }
        titulo.setAtivo(false);
        return modelMapper.map(tituloRepository.save(titulo), TituloDTO.class);
    }

    public TituloDTO able(Long id){
        Titulo titulo = modelMapper.map(findById(id), Titulo.class);
        titulo.setAtivo(true);
        return modelMapper.map(tituloRepository.save(titulo), TituloDTO.class);
    }


    private Optional<TituloDTO> converterOptionalTituloParaDTO(Optional<Titulo> optionalTitulo) {
        return optionalTitulo.map(titulo  -> modelMapper.map(titulo, TituloDTO.class));
    }
    private String findByIdDisable(Long id) {
        Optional<Titulo> titulo = tituloRepository.findByIdAndAtivo(id, false);
        if (titulo.isPresent()) {
            return "Titulo atualmente desativado";
        } else {
            return "Titulo não encontrado";
        }
    }
}
