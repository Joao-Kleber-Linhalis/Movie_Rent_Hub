package com.jk.BackEndLocadora.service;

import com.jk.BackEndLocadora.domain.Titulo;
import com.jk.BackEndLocadora.domain.Usuario;
import com.jk.BackEndLocadora.domain.dto.TituloDTO;
import com.jk.BackEndLocadora.domain.dto.UsuarioDTO;
import com.jk.BackEndLocadora.exceptions.ObjectNotFoundException;
import com.jk.BackEndLocadora.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    ModelMapper modelMapper = new ModelMapper();

    public UsuarioDTO login(UsuarioDTO usuarioDTO){
        Optional<Usuario> usuario = usuarioRepository.findByNomeAndSenha(usuarioDTO.getNome(), usuarioDTO.getSenha());
        return converterOptionalUsuarioParaDTO(usuario).orElseThrow(() -> new ObjectNotFoundException("Usuario e/ou Senha incorretos"));
    }

    public UsuarioDTO findById(Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return converterOptionalUsuarioParaDTO(usuario).orElseThrow(() -> new ObjectNotFoundException("Usuario com id: " + id + " Inexistente"));
    }

    public List<UsuarioDTO> findAll(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(usuario -> modelMapper.map(usuario,UsuarioDTO.class)).collect(Collectors.toList());
    }

    public UsuarioDTO create(UsuarioDTO usuarioDTO){
        usuarioDTO.setId(null);
        return modelMapper.map(usuarioRepository.save(modelMapper.map(usuarioDTO, Usuario.class)), UsuarioDTO.class);
    }

    public UsuarioDTO update(Long id,UsuarioDTO usuarioDTO){
        findById(id);
        usuarioDTO.setId(id);
        return modelMapper.map(usuarioRepository.save(modelMapper.map(usuarioDTO, Usuario.class)), UsuarioDTO.class);
    }

    public void delete(Long id){
        UsuarioDTO usuarioDTO = findById(id);
        usuarioRepository.delete(modelMapper.map(usuarioDTO, Usuario.class));
    }

    private Optional<UsuarioDTO> converterOptionalUsuarioParaDTO(Optional<Usuario> optionalUsuario) {
        return optionalUsuario.map(usuario  -> modelMapper.map(usuario, UsuarioDTO.class));
    }

    public void criarRegistro(){
        if(usuarioRepository.findByNome("Administrador").isEmpty()){
            Usuario adm = new Usuario();
            adm.setNome("Administrador");
            adm.setSenha("adm123");
            usuarioRepository.save(adm);
        }
    }
}
