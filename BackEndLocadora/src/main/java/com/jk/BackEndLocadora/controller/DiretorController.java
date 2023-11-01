package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.domain.dto.ClasseDTO;
import com.jk.BackEndLocadora.domain.dto.DiretorDTO;
import com.jk.BackEndLocadora.service.DiretorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "DiretorController", description = "Fornece serviços web REST para acesso e manipulação de dados de Diretores.")
public class DiretorController {

    @Autowired
    private DiretorService diretorService;

    @GetMapping(value = "/{id}")
    @Operation(description = "Retorna o Diretor de acordo com ID", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Diretor exista e esteja ativo"),
            @ApiResponse(responseCode = "404", description = "Caso o Diretor não exista e esteja inativo")})
    public ResponseEntity<DiretorDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(diretorService.findById(id));
    }

    @GetMapping("/")
    @Operation (description="Retorna todos os Diretores cadastrados que estão Ativos ou Inativos de acordo com o status.")
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
    @Operation(description = "Cadastra um novo Diretor", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Diretor seja criado"),
            @ApiResponse(responseCode = "404", description = "Caso algum erro não permita o cadastro")})
    public ResponseEntity<DiretorDTO> create(@Valid @RequestBody DiretorDTO diretorDTO) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(diretorService.create(diretorDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "Atualiza o Diretor", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Diretor seja atualizado"),
            @ApiResponse(responseCode = "400", description = "Caso alguma validação não permita a atualização")})
    public ResponseEntity<DiretorDTO> update(@PathVariable Long id, @Valid @RequestBody DiretorDTO diretorDTO) {
        return ResponseEntity.ok().body(diretorService.update(id, diretorDTO));
    }

    @PutMapping(value = "/desabilitar/{id}")
    @Operation(description = "Desabilitar o Diretor", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Diretor seja desabilitado"),
            @ApiResponse(responseCode = "400", description = "Caso possua algum titulo ativo associado a ele")})
    public ResponseEntity<DiretorDTO> desabilitar(@PathVariable Long id) {
        return ResponseEntity.ok().body(diretorService.disable(id));
    }

    @PutMapping(value = "/habilitar/{id}")
    @Operation(description = "Habilita o Diretor")
    public ResponseEntity<DiretorDTO> habilitar(@PathVariable Long id){
        return ResponseEntity.ok().body(diretorService.able(id));
    }

}
