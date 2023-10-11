package com.jk.BackEndLocadora.domain;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "diretor")
public class Diretor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo NOME é requerido")
    @Column(nullable = false)
    private String nome;

    private Boolean ativo = true;

}
