package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.domain.dto.AtorDTO;
import com.jk.BackEndLocadora.service.AtorService;
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
@RequestMapping(value = "api/atores")
public class AtorController {

    @Autowired
    private AtorService atorService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<AtorDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(atorService.findById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<AtorDTO>> findAllByStatus(@RequestParam("status") String status){
        if ("ativos".equals(status)) {
            return ResponseEntity.ok().body(atorService.findAllByStatus(true));
        } else if ("inativos".equals(status)) {
            return ResponseEntity.ok().body(atorService.findAllByStatus(false));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<AtorDTO> create(@Valid @RequestBody AtorDTO atorDTO){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(atorService.create(atorDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AtorDTO> update(@PathVariable Long id, @Valid @RequestBody AtorDTO atorDTO){
        return ResponseEntity.ok().body(atorService.update(id,atorDTO));
    }

    @PutMapping(value = "/desabilitar/{id}")
    public ResponseEntity<AtorDTO> desabilitar(@PathVariable Long id){
        return ResponseEntity.ok().body(atorService.disable(id));
    }
}
