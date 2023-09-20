package com.jk.BackEndLocadora.repository;

import com.jk.BackEndLocadora.domain.Ator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtorRepository extends JpaRepository<Ator,Long> {

    Optional<Ator> findByIdAndAtivo(Long id, boolean ativo);

    @Query("SELECT t FROM Ator t WHERE t.ativo = :status ORDER BY t.id")
    List<Ator> findByStatus(boolean status);

    Optional<Ator> findByNome(String nome);

    @Query("SELECT t.nome FROM Titulo t JOIN t.atores a WHERE a.id = :atorId")
    List<String> findTitulosByAtorId(Long atorId);
}
