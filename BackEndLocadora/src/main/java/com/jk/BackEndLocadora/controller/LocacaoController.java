package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.domain.Locacao;
import com.jk.BackEndLocadora.domain.dto.ItemDTO;
import com.jk.BackEndLocadora.domain.dto.LocacaoDTO;
import com.jk.BackEndLocadora.service.LocacaoService;
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
@RequestMapping(value = "api/locacoes")
@Tag(name = "Locacoeontroller", description = "Fornece serviços web REST para acesso e manipulação de dados de Locacoes.")
public class LocacaoController {

    @Autowired
    private LocacaoService locacaoService;

    @GetMapping(value = "/{id}")
    @Operation(description = "Retorna a Locação de acordo com ID", responses = {
            @ApiResponse(responseCode = "200", description = "Caso a Locação exista e esteja ativo"),
            @ApiResponse(responseCode = "404", description = "Caso a Locação não exista")})
    public ResponseEntity<LocacaoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(locacaoService.findById(id));
    }

    @GetMapping("/")
    @Operation(description = "Retorna todas as Locacões cadastrados que existem ou estão fechadas ou estão em aberto de acordo com o status.")
    public ResponseEntity<List<LocacaoDTO>> findAllByStatus(@RequestParam("status") String status) {
        if ("all".equals(status)) {
            return ResponseEntity.ok().body(locacaoService.findAll());
        } else if ("open".equals(status)) {
            return ResponseEntity.ok().body(locacaoService.findAllOpen());
        } else if ("closed".equals(status)) {
            return ResponseEntity.ok().body(locacaoService.findAllClose());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    @Operation(description = "Cadastra uma nova Locação", responses = {
            @ApiResponse(responseCode = "200", description = "Caso a Locação seja criada"),
            @ApiResponse(responseCode = "442", description = "Caso o Cliente possua Locação em atraso"),
            @ApiResponse(responseCode = "404", description = "Caso algum erro não permita o cadastro")})
    public ResponseEntity<LocacaoDTO> create(@Valid @RequestBody LocacaoDTO locacaoDTO){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(locacaoService.create(locacaoDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "Cadastra uma nova Locação", responses = {
            @ApiResponse(responseCode = "200", description = "Caso a Locação seja criada"),
            @ApiResponse(responseCode = "442", description = "Caso o Cliente possua Locação em atraso"),
            @ApiResponse(responseCode = "404", description = "Caso algum erro não permita o cadastro")})
    public ResponseEntity<LocacaoDTO> update(@PathVariable Long id, @Valid @RequestBody LocacaoDTO locacaoDTO){
        return ResponseEntity.ok().body(locacaoService.update(id,locacaoDTO));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "Deleta a Locação de acordo com ID", responses = {
            @ApiResponse(responseCode = "200", description = "Caso a Locação exista e esteja ativo"),
            @ApiResponse(responseCode = "404", description = "Caso a Locação não exista")})
    public ResponseEntity<Void> delete(@PathVariable Long id){
        locacaoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
