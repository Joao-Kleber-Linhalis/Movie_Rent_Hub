package com.jk.BackEndLocadora.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.BackEndLocadora.domain.enums.StatusItem;
import com.jk.BackEndLocadora.domain.enums.TipoItem;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "item")
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_serie", nullable = false)
    @NotNull(message = "O campo NUMERO DE SÉRIE é requerido")
    private Long numSerie;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "O campo DATA DE AQUISIÇÃO DE SÉRIE é requerido")
    @Column(name = "data_aquisicao", nullable = false)
    private Date dtAquisicao;

    @JsonIgnoreProperties(value = "items")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_titulo", referencedColumnName = "id", nullable = false)
    private Titulo titulo;
    @Column(name = "tipo_item", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoItem tipoItem;

    @Column(name = "status_item", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusItem statusItem = StatusItem.DISPONIVEL;
    private Boolean ativo = true;
}
