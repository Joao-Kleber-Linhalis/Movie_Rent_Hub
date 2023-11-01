package com.jk.BackEndLocadora.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
@Table(name = "dependente")
public class Dependente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo NOME é requerido")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "O campo SEXO é requerido")
    @Column(nullable = false)
    private String sexo;

    //@CPF
    @NotNull(message = "O campo CPF é requerido")
    @Column(unique = true, nullable = false)
    private String cpf;

    @NotNull(message = "O campo NASCIMENTO é requerido")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false)
    private Date nascimento;

    @JsonIgnoreProperties(value = "dependentes")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id", nullable = false)
    private Cliente cliente;

    private Boolean ativo = true;

}
