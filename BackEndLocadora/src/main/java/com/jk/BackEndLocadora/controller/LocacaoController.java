package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.service.LocacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(value = "api/locacoes")
@Tag(name = "Locacoeontroller", description = "Fornece serviços web REST para acesso e manipulação de dados de Locacoes.")
public class LocacaoController {

    @Autowired
    private LocacaoService locacaoService;
}
