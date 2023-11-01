package com.jk.BackEndLocadora.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClasseDTO implements Serializable {

    private Long id;
    private String nome;

    private Double valor;

    private Integer prazoDevolucao; //Em dias

    private Boolean ativo = true;


}
