package com.jk.BackEndLocadora.domain.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
public class DiretorDTO implements Serializable {

    private Long id;

    private String nome;

    private Boolean ativo = true;

}
