package com.jk.BackEndLocadora.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.BackEndLocadora.domain.Item;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;

@Data
@Entity
@Table(name = "locacao")
public class Locacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties(value = "dependentes",allowSetters = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id", nullable = false)
    private Cliente cliente;

    @JsonIgnoreProperties(value = "cliente",allowSetters = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dependente", referencedColumnName = "id", nullable = true)
    private Dependente dependente;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "data_locacao", nullable = false)
    private Date dtLocacao = new Date();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_item", referencedColumnName = "id",nullable = false)
    private Item item;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "data_devolucao_prevista", nullable = false)
    private LocalDateTime dtDevolucaoPrevista;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "data_devolucao_efetiva")
    private Date dtDevolucaoEfetiva;

    @Column(name = "valor_cobrado", nullable = false)
    private Double valorCobrado;

    @Column(name = "multa_cobrada")
    private Double multaCobrada;

    private Double total;

    public long getDiasAtraso() {
        if (this.dtDevolucaoEfetiva != null) {
            return ChronoUnit.DAYS.between(this.dtDevolucaoPrevista, (Temporal) this.dtDevolucaoEfetiva);
        } else {
            return 0;
        }
    }

    /*@PostLoad
    private void onLoad() {
        if (this.item != null) {
            this.dtDevolucaoPrevista = dtLocacao.plusDays(this.item.getTitulo().getClasse().getPrazoDevolucao());
            this.valorCobrado = this.item.getTitulo().getClasse().getValor();
            this.multaCobrada = getMultaCobrada();
            this.total = this.valorCobrado + this.multaCobrada;
        }
    }*/

}
