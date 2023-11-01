package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.domain.dto.DiretorDTO;
import com.jk.BackEndLocadora.domain.dto.TituloDTO;
import com.jk.BackEndLocadora.service.TituloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@Validated
@RequestMapping(value = "api/titulos")
@Tag(name = "TituloController", description = "Fornece serviços web REST para acesso e manipulação de dados de Titulos.")
public class TituloController {

    @Autowired
    private TituloService tituloService;

    @GetMapping(value = "/{id}")
    @Operation(description = "Retorna o Titulo de acordo com ID", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Titulo exista e esteja ativo"),
            @ApiResponse(responseCode = "404", description = "Caso o Titulo não exista e esteja inativo")})
    public ResponseEntity<TituloDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(tituloService.findById(id));
    }

    @GetMapping("/")
    @Operation (description="Retorna todos os Titulos cadastrados que estão Ativos ou Inativos de acordo com o status.")
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
    @Operation(description = "Cadastra um novo Titulo", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Titulo seja criado"),
            @ApiResponse(responseCode = "404", description = "Caso algum erro não permita o cadastro")})
    public ResponseEntity<TituloDTO> create(@Valid @RequestBody TituloDTO tituloDTO) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(tituloService.create(tituloDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "Atualiza o Titulo", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Titulo seja atualizado"),
            @ApiResponse(responseCode = "400", description = "Caso alguma validação não permita a atualização")})
    public ResponseEntity<TituloDTO> update(@PathVariable Long id, @Valid @RequestBody TituloDTO tituloDTO){
        return ResponseEntity.ok().body(tituloService.update(id,tituloDTO));
    }

    @PutMapping(value = "/desabilitar/{id}")
    @Operation(description = "Desabilitar o Titulo", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Titulo seja desabilitado"),
            @ApiResponse(responseCode = "400", description = "Caso possua algum item ativo associado a ele")})
    public ResponseEntity<TituloDTO> desabilitar(@PathVariable Long id) {
        return ResponseEntity.ok().body(tituloService.disable(id));
    }

    @PutMapping(value = "/habilitar/{id}")
    @Operation(description = "Habilita o Titulo")
    public ResponseEntity<TituloDTO> habilitar(@PathVariable Long id){
        return ResponseEntity.ok().body(tituloService.able(id));
    }

}
