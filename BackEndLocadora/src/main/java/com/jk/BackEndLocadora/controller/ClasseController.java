package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.domain.dto.ClasseDTO;
import com.jk.BackEndLocadora.service.ClasseService;
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
@RequestMapping(value = "api/classes")
public class ClasseController {

    @Autowired
    private ClasseService classeService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClasseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(classeService.findById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<ClasseDTO>> findAllByStatus(@RequestParam("status") String status){
        if ("ativos".equals(status)) {
            return ResponseEntity.ok().body(classeService.findAllByStatus(true));
        } else if ("inativos".equals(status)) {
            return ResponseEntity.ok().body(classeService.findAllByStatus(false));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<ClasseDTO> create(@Valid @RequestBody ClasseDTO classeDTO){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(classeService.create(classeDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClasseDTO> update(@PathVariable Long id, @Valid @RequestBody ClasseDTO classeDTO){
        return ResponseEntity.ok().body(classeService.update(id,classeDTO));
    }

    @PutMapping(value = "/desabilitar/{id}")
    public ResponseEntity<ClasseDTO> desabilitar(@PathVariable Long id){
        return ResponseEntity.ok().body(classeService.disable(id));
    }
}
