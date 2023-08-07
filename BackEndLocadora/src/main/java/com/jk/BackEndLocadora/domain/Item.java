package com.jk.BackEndLocadora.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jk.BackEndLocadora.domain.enums.StatusItem;
import com.jk.BackEndLocadora.domain.enums.TipoItem;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "item")
public class Item {

    @Id
    @Column(name = "numero_serie")
    @NotNull(message = "O campo NUMERO DE SÉRIE é requerido")
    private Long numSerie;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "O campo DATA DE AQUISIÇÃO DE SÉRIE é requerido")
    @Column(name = "data_aquisicao")
    private LocalDateTime dtAquisicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_titulo", referencedColumnName = "id", nullable = false)
    private Titulo titulo;
    @Column(name = "tipo_item")
    @Enumerated(EnumType.STRING)
    private TipoItem tipoItem;

    @Column(name = "status_item")
    @Enumerated(EnumType.STRING)
    private StatusItem statusItem = StatusItem.DISPONIVEL;
    private Boolean ativo = true;
}
