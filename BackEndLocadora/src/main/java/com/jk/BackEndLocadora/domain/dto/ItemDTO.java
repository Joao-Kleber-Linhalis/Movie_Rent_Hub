package com.jk.BackEndLocadora.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.BackEndLocadora.domain.Titulo;
import com.jk.BackEndLocadora.domain.enums.StatusItem;
import com.jk.BackEndLocadora.domain.enums.TipoItem;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ItemDTO implements Serializable {

    private Long id;

    @NotNull(message = "O campo NUMERO DE SÉRIE é requerido")
    private Long numSerie;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "O campo DATA DE AQUISIÇÃO é requerido")
    private LocalDateTime dtAquisicao;

    @JsonIgnoreProperties(value = "items")
    private TituloDTO titulo;

    @NotNull(message = "O campo TIPO ITEM é requerido")
    private TipoItem tipoItem;

    private StatusItem statusItem;
    private Boolean ativo = true;
}
