package com.jk.BackEndLocadora.repository;

import com.jk.BackEndLocadora.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Optional<Usuario> findByNomeAndSenha(String nome,String senha);

    Optional<Usuario> findByNome(String nome);
}
