package com.jk.BackEndLocadora.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
public class UsuarioDTO implements Serializable {

    private Long id;

    private String nome;

    private String senha;
}
