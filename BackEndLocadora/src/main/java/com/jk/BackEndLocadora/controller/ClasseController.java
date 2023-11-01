package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.domain.dto.AtorDTO;
import com.jk.BackEndLocadora.domain.dto.ClasseDTO;
import com.jk.BackEndLocadora.service.ClasseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(value = "api/classes")
@Tag(name = "Classeontroller", description = "Fornece serviços web REST para acesso e manipulação de dados de Classes.")
public class ClasseController {

    @Autowired
    private ClasseService classeService;

    @GetMapping(value = "/{id}")
    @Operation(description = "Retorna a Classe de acordo com ID", responses = {
            @ApiResponse(responseCode = "200", description = "Caso a Classe exista e esteja ativo"),
            @ApiResponse(responseCode = "404", description = "Caso a Classe não exista e esteja inativo")})
    public ResponseEntity<ClasseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(classeService.findById(id));
    }

    @GetMapping("/")
    @Operation (description="Retorna todas as Classes cadastradas que estão Ativas ou Inativas de acordo com o status.")
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
    @Operation(description = "Cadastra uma nova Classe", responses = {
            @ApiResponse(responseCode = "200", description = "Caso a Classe seja criado"),
            @ApiResponse(responseCode = "404", description = "Caso algum erro não permita o cadastro")})
    public ResponseEntity<ClasseDTO> create(@Valid @RequestBody ClasseDTO classeDTO){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(classeService.create(classeDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "Atualiza a Classe", responses = {
            @ApiResponse(responseCode = "200", description = "Caso a Classe seja atualizada"),
            @ApiResponse(responseCode = "400", description = "Caso alguma validação não permita a atualização")})
    public ResponseEntity<ClasseDTO> update(@PathVariable Long id, @Valid @RequestBody ClasseDTO classeDTO){
        return ResponseEntity.ok().body(classeService.update(id,classeDTO));
    }

    @PutMapping(value = "/desabilitar/{id}")
    @Operation(description = "Desabilitar a Classe", responses = {
            @ApiResponse(responseCode = "200", description = "Caso a Classe seja desabilitada"),
            @ApiResponse(responseCode = "400", description = "Caso possua algum titulo ativo associado a ela")})
    public ResponseEntity<ClasseDTO> desabilitar(@PathVariable Long id){
        return ResponseEntity.ok().body(classeService.disable(id));
    }

    @PutMapping(value = "/habilitar/{id}")
    @Operation(description = "Habilita a Classe")
    public ResponseEntity<ClasseDTO> habilitar(@PathVariable Long id){
        return ResponseEntity.ok().body(classeService.able(id));
    }
}
