package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.service.TituloService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/titulos")
public class TituloController {

    @Autowired
    private TituloService tituloService;
}
