package com.jk.BackEndLocadora.service;

import com.jk.BackEndLocadora.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IniciarAplicacaoService {

    @Autowired
    private UsuarioService usuarioService;

    public void IniciarAplicacao(){
        usuarioService.criarRegistro();
    }
}
