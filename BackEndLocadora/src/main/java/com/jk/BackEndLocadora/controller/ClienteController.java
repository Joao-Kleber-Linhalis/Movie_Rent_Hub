package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.domain.Cliente;
import com.jk.BackEndLocadora.domain.dto.ClasseDTO;
import com.jk.BackEndLocadora.domain.dto.ClienteDTO;
import com.jk.BackEndLocadora.service.ClienteService;
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

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(value = "api/clientes")
@Tag(name = "ClienteController", description = "Fornece serviços web REST para acesso e manipulação de dados de clientes.")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping(value = "/{id}")
    @Operation(description = "Retorna o Cliente de acordo com ID", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Cliente exista e esteja ativo"),
            @ApiResponse(responseCode = "404", description = "Caso o Cliente não exista e esteja inativo")})
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(clienteService.findById(id));
    }

    @GetMapping("/")
    @Operation (description="Retorna todos os Clientes cadastrados que estão Ativos ou Inativos de acordo com o status.")
    public ResponseEntity<List<ClienteDTO>> findAllByStatus(@RequestParam("status") String status){
        if ("ativos".equals(status)) {
            return ResponseEntity.ok().body(clienteService.findAllByStatus(true));
        } else if ("inativos".equals(status)) {
            return ResponseEntity.ok().body(clienteService.findAllByStatus(false));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    @Operation(description = "Cadastra um novo Cliente", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Cliente seja criado"),
            @ApiResponse(responseCode = "404", description = "Caso algum erro não permita o cadastro")})
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO clienteDTO){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(clienteService.create(clienteDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "Atualiza o Cliente", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Cliente seja atualizado"),
            @ApiResponse(responseCode = "400", description = "Caso alguma validação não permita a atualização")})
    public ResponseEntity<ClienteDTO> update(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO){
        return ResponseEntity.ok().body(clienteService.update(id,clienteDTO));
    }

    @PutMapping(value = "/desabilitar/{id}")
    @Operation(description = "Desabilitar o Cliente", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Cliente seja desabilitado"),
            @ApiResponse(responseCode = "400", description = "Caso possua algum dependente ativo associado a ele")})
    public ResponseEntity<ClienteDTO> desabilitar(@PathVariable Long id){
        return ResponseEntity.ok().body(clienteService.disable(id));
    }

    @PutMapping(value = "/habilitar/{id}")
    @Operation(description = "Habilita o Cliente")
    public ResponseEntity<ClienteDTO> habilitar(@PathVariable Long id){
        return ResponseEntity.ok().body(clienteService.able(id));
    }
}
