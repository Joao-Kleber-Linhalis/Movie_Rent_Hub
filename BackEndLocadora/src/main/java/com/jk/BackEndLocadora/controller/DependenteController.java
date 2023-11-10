package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.domain.dto.ClienteDTO;
import com.jk.BackEndLocadora.domain.dto.DependenteDTO;
import com.jk.BackEndLocadora.service.DependenteService;
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
@RequestMapping(value = "api/dependentes")
@Tag(name = "DependenteController", description = "Fornece serviços web REST para acesso e manipulação de dados de Dependentes.")

public class DependenteController {

    @Autowired
    private DependenteService dependenteService;

    @GetMapping(value = "/{id}")
    @Operation(description = "Retorna o Dependente de acordo com ID", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Dependente exista e esteja ativo"),
            @ApiResponse(responseCode = "404", description = "Caso o Dependente não exista ou esteja inativo")})
    public ResponseEntity<DependenteDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(dependenteService.findById(id));
    }

    @GetMapping("/")
    @Operation(description="Retorna todos os Dependentes cadastrados que estão Ativos ou Inativos de acordo com o status.")
    public ResponseEntity<List<DependenteDTO>> findAllByStatus(@RequestParam("status") String status){
        if ("ativos".equals(status)) {
            return ResponseEntity.ok().body(dependenteService.findAllByStatus(true));
        } else if ("inativos".equals(status)) {
            return ResponseEntity.ok().body(dependenteService.findAllByStatus(false));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    @Operation(description = "Cadastra um novo Dependente", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Dependente seja criado"),
            @ApiResponse(responseCode = "404", description = "Caso algum erro não permita o cadastro")})
    public ResponseEntity<DependenteDTO> create(@Valid @RequestBody DependenteDTO dependenteDTO){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(dependenteService.create(dependenteDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "Atualiza o Dependente", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Dependente seja atualizado"),
            @ApiResponse(responseCode = "400", description = "Caso alguma validação não permita a atualização")})
    public ResponseEntity<DependenteDTO> update(@PathVariable Long id, @Valid @RequestBody DependenteDTO dependenteDTO){
        return ResponseEntity.ok().body(dependenteService.update(id,dependenteDTO));
    }

    @PutMapping(value = "/desabilitar/{id}")
    @Operation(description = "Desabilita o Dependente")
    public ResponseEntity<DependenteDTO> desabilitar(@PathVariable Long id){
        return ResponseEntity.ok().body(dependenteService.disable(id));
    }

    @PutMapping(value = "/habilitar/{id}")
    @Operation(description = "Habilita o Dependente", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Dependente seja habilitado"),
            @ApiResponse(responseCode = "400", description = "Caso possua 3 dependente ativo associado ao cliente")})
    public ResponseEntity<DependenteDTO> habilitar(@PathVariable Long id){
        return ResponseEntity.ok().body(dependenteService.able(id));
    }
}
