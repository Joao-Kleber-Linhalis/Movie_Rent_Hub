package com.jk.BackEndLocadora.repository;

import com.jk.BackEndLocadora.domain.Ator;
import com.jk.BackEndLocadora.domain.Classe;
import com.jk.BackEndLocadora.domain.Dependente;
import com.jk.BackEndLocadora.domain.Diretor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiretorRepository extends JpaRepository<Diretor,Long> {

    Optional<Diretor> findByIdAndAtivo(Long id, boolean ativo);

    @Query("SELECT t FROM Diretor t WHERE t.ativo = :status")
    List<Diretor> findByStatus(boolean status);

    Optional<Diretor> findByNome(String nome);

    @Query("SELECT t.nome FROM Titulo t JOIN t.diretor a WHERE a.id = :diretorId")
    List<String> findTitulosByDiretorId(Long diretorId);
}
