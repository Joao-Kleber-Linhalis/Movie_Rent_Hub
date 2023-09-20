package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.domain.dto.ClasseDTO;
import com.jk.BackEndLocadora.domain.dto.DiretorDTO;
import com.jk.BackEndLocadora.service.DiretorService;
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
@RequestMapping(value = "api/diretores")
public class DiretorController {

    @Autowired
    private DiretorService diretorService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<DiretorDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(diretorService.findById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<DiretorDTO>> findAllByStatus(@RequestParam("status") String status) {
        if ("ativos".equals(status)) {
            return ResponseEntity.ok().body(diretorService.findAllByStatus(true));
        } else if ("inativos".equals(status)) {
            return ResponseEntity.ok().body(diretorService.findAllByStatus(false));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<DiretorDTO> create(@Valid @RequestBody DiretorDTO diretorDTO) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(diretorService.create(diretorDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DiretorDTO> update(@PathVariable Long id, @Valid @RequestBody DiretorDTO diretorDTO) {
        return ResponseEntity.ok().body(diretorService.update(id, diretorDTO));
    }

    @PutMapping(value = "/desabilitar/{id}")
    public ResponseEntity<DiretorDTO> desabilitar(@PathVariable Long id) {
        return ResponseEntity.ok().body(diretorService.disable(id));
    }

    @PutMapping(value = "/habilitar/{id}")
    public ResponseEntity<DiretorDTO> habilitar(@PathVariable Long id){
        return ResponseEntity.ok().body(diretorService.able(id));
    }

}
