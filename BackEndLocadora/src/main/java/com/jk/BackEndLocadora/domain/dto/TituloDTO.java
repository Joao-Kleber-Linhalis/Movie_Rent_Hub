package com.jk.BackEndLocadora.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.BackEndLocadora.domain.Item;
import com.jk.BackEndLocadora.domain.enums.CategoriaFilme;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class TituloDTO implements Serializable {

    private Long id;

    private String nome;

    private Long ano;

    private String sinopse;

    private String capa;

    @ElementCollection(targetClass = CategoriaFilme.class)
    @Enumerated(EnumType.STRING)
    private Set<CategoriaFilme> categorias;

    @JsonIgnoreProperties(value = "titulo",allowSetters = true)
    private List<ItemDTO> items = new ArrayList<>();

    private DiretorDTO diretor;
    private ClasseDTO classe;


    private List<AtorDTO> atores;
    private Boolean ativo = true;
}

