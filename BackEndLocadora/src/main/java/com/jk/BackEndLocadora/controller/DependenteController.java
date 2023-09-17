package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.service.DependenteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/dependentes")
public class DependenteController {

    @Autowired
    private DependenteService dependenteService;
}
