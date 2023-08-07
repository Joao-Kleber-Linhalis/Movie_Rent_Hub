package com.jk.BackEndLocadora.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoriaFilme {
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

    private final String descricao;
}