package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(value = "api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
}
