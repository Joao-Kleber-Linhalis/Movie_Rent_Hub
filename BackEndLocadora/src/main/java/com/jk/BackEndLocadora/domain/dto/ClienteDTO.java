package com.jk.BackEndLocadora.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.BackEndLocadora.domain.Dependente;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ClienteDTO implements Serializable {

    private Long id;

    @NotNull(message = "O campo NOME é requerido")
    private String nome;

    @NotNull(message = "O campo ENDEREÇO é requerido")
    private String endereco;
    @NotNull(message = "O campo TELEFONE é requerido")
    private String telefone;
    @NotNull(message = "O campo SEXO é requerido")
    private String sexo;

    //@CPF
    @NotNull(message = "O campo CPF é requerido")
    private String cpf;

    @NotNull(message = "O campo NASCIMENTO é requerido")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime nascimento;

    private Boolean ativo = true;

    @JsonIgnoreProperties(value = "cliente")
    private List<DependenteDTO> dependentes = new ArrayList<>();

}
