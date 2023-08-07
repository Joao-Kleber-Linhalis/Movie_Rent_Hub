package com.jk.BackEndLocadora.domain;

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
@Entity
@Table(name = "locacao")
public class Locacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "data_locacao")
    private LocalDateTime dtLocacao = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "o campo ITEM é requerido")
    @JoinColumn(name = "numSerie_item", referencedColumnName = "numero_serie")
    private Item item;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "data_devolucao_prevista")
    private LocalDateTime dtDevolucaoPrevista;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "data_devolucao_efetiva")
    private LocalDateTime dtDevolucaoEfetiva;

    @Column(name = "valor_cobrado")
    private Double valorCobrado;

    @Column(name = "multa_cobrada")
    private Double multaCobrada;

    private Double total;

    public long getDiasAtraso() {
        if (this.dtDevolucaoEfetiva != null) {
            return ChronoUnit.DAYS.between(this.dtDevolucaoPrevista, this.dtDevolucaoEfetiva);
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
