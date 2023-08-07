package com.jk.BackEndLocadora.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoItem {
    VHS("VHS"),
    DVD("DVD"),
    BLU_RAY("Blu-ray"),
    DIGITAL("Digital"),
    OUTRO("Outro");

    private final String descricao;

}

