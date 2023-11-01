package com.jk.BackEndLocadora.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.BackEndLocadora.domain.Cliente;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class DependenteDTO implements Serializable {

    private Long id;

    @NotNull(message = "O campo NOME é requerido")
    private String nome;

    @NotNull(message = "O campo SEXO é requerido")
    private String sexo;

    //@CPF
    @NotNull(message = "O campo CPF é requerido")
    private String cpf;

    @NotNull(message = "O campo NASCIMENTO é requerido")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date nascimento;

    @JsonIgnoreProperties(value = "dependentes")
    private ClienteDTO cliente;

    private Boolean ativo = true;

}
