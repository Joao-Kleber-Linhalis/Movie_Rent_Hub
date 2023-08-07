package com.jk.BackEndLocadora.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusItem {
    DISPONIVEL("Disponível"),
    ALUGADO("Alugado");

    private final String descricao;
}
