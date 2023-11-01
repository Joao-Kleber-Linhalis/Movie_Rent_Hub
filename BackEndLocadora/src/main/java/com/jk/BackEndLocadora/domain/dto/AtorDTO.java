package com.jk.BackEndLocadora.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AtorDTO implements Serializable {

    private Long id;
    private String nome;
    private Boolean ativo = true;
}
