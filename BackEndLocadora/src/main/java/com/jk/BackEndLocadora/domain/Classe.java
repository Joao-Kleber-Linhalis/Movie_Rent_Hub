package com.jk.BackEndLocadora.domain;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "classe")
public class Classe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "O campo NOME é requerido")
    private String nome;

    @NotNull(message = "O campo VALOR é requerido")
    private Double valor;

    @Column(name = "prazo_devolucao")
    @NotNull(message = "O campo PRAZO DEVOLUÇÃO é requerido")
    private Integer prazoDevolucao; //Em dias

    private Boolean ativo = true;


}
