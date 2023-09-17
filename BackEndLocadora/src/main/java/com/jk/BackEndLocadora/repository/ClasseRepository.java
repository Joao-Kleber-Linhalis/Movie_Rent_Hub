package com.jk.BackEndLocadora.repository;

import com.jk.BackEndLocadora.domain.Ator;
import com.jk.BackEndLocadora.domain.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClasseRepository extends JpaRepository<Classe,Long> {

    Optional<Classe> findByIdAndAtivo(Long id, boolean ativo);

    @Query("SELECT t FROM Classe t WHERE t.ativo = :status")
    List<Classe> findByStatus(boolean status);

    Optional<Classe> findByNome(String nome);

    @Query("SELECT t.nome FROM Titulo t JOIN t.classe a WHERE a.id = :classeId")
    List<String> findTitulosByClasseId(Long classeId);
}
