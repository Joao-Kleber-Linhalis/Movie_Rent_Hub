package com.jk.BackEndLocadora.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.BackEndLocadora.domain.Dependente;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ClienteDTO implements Serializable {

    private Long id;

    private String nome;

    private String endereco;
    private String telefone;
    private String sexo;

    //@CPF
    private String cpf;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date nascimento;

    private Boolean ativo = true;

    @JsonIgnoreProperties(value = "cliente",allowSetters = true)
    private List<DependenteDTO> dependentes = new ArrayList<>();

}
