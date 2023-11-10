package com.jk.BackEndLocadora.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.BackEndLocadora.domain.Cliente;
import com.jk.BackEndLocadora.domain.Dependente;
import com.jk.BackEndLocadora.domain.Item;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Data
public class LocacaoDTO implements Serializable {
    private Long id;

    @JsonIgnoreProperties(value = "dependentes",allowSetters = true)
    private ClienteDTO cliente;

    @JsonIgnoreProperties(value = "cliente",allowSetters = true)
    private DependenteDTO dependente;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dtLocacao = LocalDateTime.now();

    private ItemDTO item;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date dtDevolucaoPrevista;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dtDevolucaoEfetiva;

    private Double valorCobrado;

    private Double multaCobrada;

    private Double total;


}
