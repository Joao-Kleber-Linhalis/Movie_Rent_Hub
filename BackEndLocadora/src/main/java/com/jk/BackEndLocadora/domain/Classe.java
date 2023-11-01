package com.jk.BackEndLocadora.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "classe")
public class Classe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Double valor;

    @Column(name = "prazo_devolucao",nullable = false)
    private Integer prazoDevolucao; //Em dias

    private Boolean ativo = true;


}
