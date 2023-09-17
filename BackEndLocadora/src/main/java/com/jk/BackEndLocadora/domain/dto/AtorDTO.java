package com.jk.BackEndLocadora.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AtorDTO implements Serializable {

    private Long id;
    @NotNull(message = "O campo NOME é requerido")
    private String nome;
    private Boolean ativo = true;
}
