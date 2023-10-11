package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.domain.Item;
import com.jk.BackEndLocadora.domain.dto.DiretorDTO;
import com.jk.BackEndLocadora.domain.dto.ItemDTO;
import com.jk.BackEndLocadora.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(value = "api/itens")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ItemDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(itemService.findById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<ItemDTO>> findAllByStatus(@RequestParam("status") String status) {
        if ("ativos".equals(status)) {
            return ResponseEntity.ok().body(itemService.findAllByStatus(true));
        } else if ("inativos".equals(status)) {
            return ResponseEntity.ok().body(itemService.findAllByStatus(false));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<ItemDTO> create(@Valid @RequestBody ItemDTO itemDTO){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(itemService.create(itemDTO).getNumSerie()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ItemDTO> update(@PathVariable Long id, @Valid @RequestBody ItemDTO itemDTO){
        return ResponseEntity.ok().body(itemService.update(id,itemDTO));
    }

    @PutMapping(value = "/alterar/{id}")
    public ResponseEntity<ItemDTO> changeAtivo(@PathVariable Long id){
        return ResponseEntity.ok().body(itemService.changeAtivo(id));
    }
}
