package com.jk.BackEndLocadora.controller;

import com.jk.BackEndLocadora.domain.dto.AtorDTO;
import com.jk.BackEndLocadora.domain.dto.UsuarioDTO;
import com.jk.BackEndLocadora.service.UsuarioService;
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
@RequestMapping(value = "api/usuarios")
@Tag(name = "UsuarioController", description = "Fornece serviços web REST para acesso e manipulação de dados de Usuarios.")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(value = "/login")
    @Operation(description = "Retorna o usuario para logar", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o usuario possa logar"),
            @ApiResponse(responseCode = "404", description = "Caso o usuario não exista")})
    public ResponseEntity<UsuarioDTO> login(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok().body(usuarioService.login(usuarioDTO));
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "Retorna o usuario de acordo com ID", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o usuario exista"),
            @ApiResponse(responseCode = "404", description = "Caso o usuario não exista")})
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(usuarioService.findById(id));
    }

    @GetMapping()
    @Operation (description="Retorna todos os usuarios cadastrados")
    public ResponseEntity<List<UsuarioDTO>> findAll(){
        return ResponseEntity.ok().body(usuarioService.findAll());
    }

    @PostMapping
    @Operation(description = "Cadastra um novo usuario", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o usuario seja criado"),
            @ApiResponse(responseCode = "404", description = "Caso algum erro não permita o cadastro")})
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioDTO usuarioDTO){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(usuarioService.create(usuarioDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "Atualiza o Usuario", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Usuario seja atualizado"),
            @ApiResponse(responseCode = "400", description = "Caso alguma validação não permita a atualização")})
    public ResponseEntity<UsuarioDTO> update(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok().body(usuarioService.update(id,usuarioDTO));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "Deleta o Usuario", responses = {
            @ApiResponse(responseCode = "200", description = "Caso o Usuario seja deletado"),
            @ApiResponse(responseCode = "400", description = "Caso alguma validação não permita a exclusão")})
    public ResponseEntity<Void> delete(@PathVariable Long id){
        usuarioService.delete(id);
        return ResponseEntity.ok().build();
    }


}
