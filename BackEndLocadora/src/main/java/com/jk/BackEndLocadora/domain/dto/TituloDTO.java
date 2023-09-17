package com.jk.BackEndLocadora.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.BackEndLocadora.domain.Ator;
import com.jk.BackEndLocadora.domain.Classe;
import com.jk.BackEndLocadora.domain.Diretor;
import com.jk.BackEndLocadora.domain.Item;
import com.jk.BackEndLocadora.domain.enums.CategoriaFilme;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class TituloDTO implements Serializable {

    private Long id;

    @NotNull(message = "O campo NOME é requerido")
    private String nome;

    @NotNull(message = "O campo ANO é requerido")
    private Long ano;

    @NotNull(message = "O campo SINOPSE é requerido")
    private String sinopse;

    private Byte[] capa;

    @NotNull(message = "O campo CATEGORIA precisa de pelo menos 1 (uma) categoria")
    private Set<CategoriaFilme> categorias;

    @JsonIgnoreProperties(value = "titulo")
    private List<Item> items = new ArrayList<>();
    @NotNull(message = "O campo DIRETOR é requerido")
    private DiretorDTO diretor;
    @NotNull(message = "O campo CLASSE é requerido")
    private ClasseDTO classe;

    private List<AtorDTO> atores;
    private Boolean ativo = true;
}
