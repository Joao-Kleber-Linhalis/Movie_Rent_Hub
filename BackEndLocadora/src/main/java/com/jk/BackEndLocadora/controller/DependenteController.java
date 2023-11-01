package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.service.DependenteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(value = "api/dependentes")
@Tag(name = "DependenteController", description = "Fornece serviços web REST para acesso e manipulação de dados de Dependentes.")

public class DependenteController {

    @Autowired
    private DependenteService dependenteService;
}
