package com.jk.BackEndLocadora.repository;

import com.jk.BackEndLocadora.domain.Ator;
import com.jk.BackEndLocadora.domain.Diretor;
import com.jk.BackEndLocadora.domain.Locacao;
import com.jk.BackEndLocadora.domain.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TituloRepository extends JpaRepository<Titulo,Long> {

    Optional<Titulo> findByIdAndAtivo(Long id, boolean ativo);

    @Query("SELECT t FROM Titulo t WHERE t.ativo = :status ORDER BY t.id")
    List<Titulo> findByStatus(boolean status);

    @Query("SELECT COUNT(i) FROM Titulo t JOIN t.items i WHERE t.id = :tituloId AND i.ativo = true")
    Long countItensAtivosPorTituloId(Long tituloId);
}
