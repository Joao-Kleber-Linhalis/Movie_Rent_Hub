package com.jk.BackEndLocadora.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ClasseDTO implements Serializable {

    private Long id;
    @NotNull(message = "O campo NOME é requerido")
    private String nome;

    @NotNull(message = "O campo VALOR é requerido")
    private Double valor;

    @NotNull(message = "O campo PRAZO DEVOLUÇÃO é requerido")
    private Integer prazoDevolucao; //Em dias

    private Boolean ativo = true;


}
