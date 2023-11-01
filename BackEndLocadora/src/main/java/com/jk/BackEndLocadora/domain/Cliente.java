package com.jk.BackEndLocadora.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo NOME é requerido")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "O campo ENDEREÇO é requerido")
    @Column(nullable = false)
    private String endereco;
    @NotNull(message = "O campo TELEFONE é requerido")
    @Column(nullable = false)
    private String telefone;
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

    private Boolean ativo = true;

    @JsonIgnoreProperties(value = "cliente")
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "cliente",cascade = CascadeType.ALL)
    private List<Dependente> dependentes = new ArrayList<>();

    public Integer qtdDependentesAtivos(){
        Integer qtdAtivos = 0;
        for (Dependente dependente : dependentes){
            if(dependente.getAtivo()){
                qtdAtivos++;
            }
        }
        return qtdAtivos;
    }

}
