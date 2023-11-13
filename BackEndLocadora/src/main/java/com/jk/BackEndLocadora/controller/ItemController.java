package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.domain.Item;
import com.jk.BackEndLocadora.domain.dto.DiretorDTO;
import com.jk.BackEndLocadora.domain.dto.ItemDTO;
import com.jk.BackEndLocadora.service.ItemService;
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
@RequestMapping(value = "api/itens")
@Tag(name = "ItemController", description = "Fornece serviços web REST para acesso e manipulação de dados de Itens.")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/{id}")
    @Operation(description = "Retorna o Item de acordo com ID", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Item exista e esteja ativo"),
            @ApiResponse(responseCode = "404", description = "Caso o Item não exista e esteja inativo")})
    public ResponseEntity<ItemDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(itemService.findById(id));
    }

    @GetMapping(value = "/disponivel/{id}")
    @Operation(description = "Retorna o Item de acordo com ID", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Item exista, esteja ativo e disponivel"),
            @ApiResponse(responseCode = "404", description = "Caso o Item não exista e esteja inativo")})
    public ResponseEntity<ItemDTO> findByIdDisponivel(@PathVariable Long id){
        return ResponseEntity.ok().body(itemService.findByIdDisponivel(id));
    }

    @GetMapping("/")
    @Operation (description="Retorna todos os Itens cadastrados que estão Ativos ou Inativos de acordo com o status.")
    public ResponseEntity<List<ItemDTO>> findAllByStatus(@RequestParam("status") String status) {
        if ("ativos".equals(status)) {
            return ResponseEntity.ok().body(itemService.findAllByStatus(true));
        } else if ("inativos".equals(status)) {
            return ResponseEntity.ok().body(itemService.findAllByStatus(false));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    @Operation(description = "Cadastra um novo Item", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Item seja criado"),
            @ApiResponse(responseCode = "404", description = "Caso algum erro não permita o cadastro")})
    public ResponseEntity<ItemDTO> create(@Valid @RequestBody ItemDTO itemDTO){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(itemService.create(itemDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "Atualiza o Item", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Item seja atualizado"),
            @ApiResponse(responseCode = "400", description = "Caso alguma validação não permita a atualização")})
    public ResponseEntity<ItemDTO> update(@PathVariable Long id, @Valid @RequestBody ItemDTO itemDTO){
        return ResponseEntity.ok().body(itemService.update(id,itemDTO));
    }

    @PutMapping(value = "/alterar/{id}")
    @Operation(description = "Altera o Ativo do Item de acordo com ID", responses = {
            @ApiResponse(responseCode = "200", description = "Caso tenha ocorrido corretamento"),
            @ApiResponse(responseCode = "404", description = "Caso o Item não exista")})
    public ResponseEntity<ItemDTO> changeAtivo(@PathVariable Long id){
        return ResponseEntity.ok().body(itemService.changeAtivo(id));
    }
}
