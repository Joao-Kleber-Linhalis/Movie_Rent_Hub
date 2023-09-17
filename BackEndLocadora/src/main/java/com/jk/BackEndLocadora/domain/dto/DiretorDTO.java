package com.jk.BackEndLocadora.domain.dto;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class DiretorDTO implements Serializable {

    private Long id;

    @NotNull(message = "O campo NOME é requerido")
    private String nome;

    private Boolean ativo = true;

}
