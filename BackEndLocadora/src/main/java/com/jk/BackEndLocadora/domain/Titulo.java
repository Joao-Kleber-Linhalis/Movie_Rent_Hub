package com.jk.BackEndLocadora.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.BackEndLocadora.domain.enums.CategoriaFilme;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "titulo", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome", "ano", "id_diretor"})
})
public class Titulo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Long ano;

    @Column(nullable = false)
    private String sinopse;
    @Lob
    @Column(name = "capa", nullable = true)
    private String capa;

    @ElementCollection(targetClass = CategoriaFilme.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_filme", nullable = false)
    private Set<CategoriaFilme> categorias;

    @JsonIgnoreProperties(value = "titulo")
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "titulo",cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_diretor", referencedColumnName = "id", nullable = false)
    private Diretor diretor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_classe", referencedColumnName = "id", nullable = false)
    private Classe classe;

    @ManyToMany
    @JoinTable(name = "filme_atores", joinColumns = {@JoinColumn(name = "filme_id")}, inverseJoinColumns = {@JoinColumn(name = "ator_id")})
    private List<Ator> atores;
    private Boolean ativo = true;
}
