package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(value = "api/itens")
public class ItemController {

    @Autowired
    private ItemService itemService;
}
