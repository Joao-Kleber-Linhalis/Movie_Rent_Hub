package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.domain.dto.AtorDTO;
import com.jk.BackEndLocadora.service.AtorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(value = "api/atores")
@Tag(name = "AtorController", description = "Fornece serviços web REST para acesso e manipulação de dados de Atores.")

public class AtorController {

    @Autowired
    private AtorService atorService;

    @GetMapping(value = "/{id}")
    @Operation(description = "Retorna o Ator de acordo com ID", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o ator exista e esteja ativo"),
            @ApiResponse(responseCode = "404", description = "Caso o ator não exista e esteja inativo")})
    public ResponseEntity<AtorDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(atorService.findById(id));
    }

    @GetMapping("/")
    @Operation (description="Retorna todos os atores cadastrados que estão Ativos ou Inativos de acordo com o status.")
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
    @Operation(description = "Cadastra um novo Ator", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Ator seja criado"),
            @ApiResponse(responseCode = "404", description = "Caso algum erro não permita o cadastro")})
    public ResponseEntity<AtorDTO> create(@Valid @RequestBody AtorDTO atorDTO){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(atorService.create(atorDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "Atualiza o Ator", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Ator seja atualizado"),
            @ApiResponse(responseCode = "400", description = "Caso alguma validação não permita a atualização")})
    public ResponseEntity<AtorDTO> update(@PathVariable Long id, @Valid @RequestBody AtorDTO atorDTO){
        return ResponseEntity.ok().body(atorService.update(id,atorDTO));
    }

    @PutMapping(value = "/desabilitar/{id}")
    @Operation(description = "Desabilitar o Ator", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Ator seja desabilitado"),
            @ApiResponse(responseCode = "400", description = "Caso possua algum titulo ativo associado a ele")})
    public ResponseEntity<AtorDTO> desabilitar(@PathVariable Long id){
        return ResponseEntity.ok().body(atorService.disable(id));
    }

    @PutMapping(value = "/habilitar/{id}")
    @Operation(description = "Habilita o Ator")
    public ResponseEntity<AtorDTO> habilitar(@PathVariable Long id){
        return ResponseEntity.ok().body(atorService.able(id));
    }
}
