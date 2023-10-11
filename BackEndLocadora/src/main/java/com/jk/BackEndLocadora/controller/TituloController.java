package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.domain.dto.DiretorDTO;
import com.jk.BackEndLocadora.domain.dto.TituloDTO;
import com.jk.BackEndLocadora.service.TituloService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@Validated
@RequestMapping(value = "api/titulos")
public class TituloController {

    @Autowired
    private TituloService tituloService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<TituloDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(tituloService.findById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<TituloDTO>> findAllByStatus(@RequestParam("status") String status) {
        if ("ativos".equals(status)) {
            return ResponseEntity.ok().body(tituloService.findAllByStatus(true));
        } else if ("inativos".equals(status)) {
            return ResponseEntity.ok().body(tituloService.findAllByStatus(false));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<TituloDTO> create(@Valid @RequestBody TituloDTO tituloDTO) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(tituloService.create(tituloDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TituloDTO> update(@PathVariable Long id, @Valid @RequestBody TituloDTO tituloDTO){
        return ResponseEntity.ok().body(tituloService.update(id,tituloDTO));
    }

    @PutMapping(value = "/desabilitar/{id}")
    public ResponseEntity<TituloDTO> desabilitar(@PathVariable Long id) {
        return ResponseEntity.ok().body(tituloService.disable(id));
    }

    @PutMapping(value = "/habilitar/{id}")
    public ResponseEntity<TituloDTO> habilitar(@PathVariable Long id){
        return ResponseEntity.ok().body(tituloService.able(id));
    }

}
