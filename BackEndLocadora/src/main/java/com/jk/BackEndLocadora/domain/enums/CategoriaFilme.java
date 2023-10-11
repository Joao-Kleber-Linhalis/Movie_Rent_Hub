package com.jk.BackEndLocadora.domain.enums;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum CategoriaFilme implements Serializable {
    ACAO("Ação"),
    COMEDIA("Comédia"),
    DRAMA("Drama"),
    FICCAO_CIENTIFICA("Ficção Científica"),
    TERROR("Terror"),
    ANIMACAO("Animação"),
    ROMANCE("Romance"),
    AVENTURA("Aventura"),
    DOCUMENTARIO("Documentário"),
    OUTRA("Outra");

    @Id
    private final String descricao;
}