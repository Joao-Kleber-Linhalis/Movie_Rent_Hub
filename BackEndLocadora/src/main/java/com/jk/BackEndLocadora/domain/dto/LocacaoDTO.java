package com.jk.BackEndLocadora.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jk.BackEndLocadora.domain.Item;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class LocacaoDTO implements Serializable {
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dtLocacao = LocalDateTime.now();

    @NotNull(message = "o campo ITEM é requerido")
    private ItemDTO item;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dtDevolucaoPrevista;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dtDevolucaoEfetiva;

    private Double valorCobrado;

    private Double multaCobrada;

    private Double total;


}
