package com.jk.BackEndLocadora.repository;

import com.jk.BackEndLocadora.domain.Item;
import com.jk.BackEndLocadora.domain.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocacaoRepository extends JpaRepository<Locacao,Long> {

    @Query("SELECT l FROM Locacao l WHERE l.dtDevolucaoEfetiva IS NULL")
    List<Locacao> findAllByDtDevolucaoEfetivaIsNull();

    @Query("SELECT l FROM Locacao l WHERE l.dtDevolucaoEfetiva IS NOT NULL")
    List<Locacao> findAllByDtDevolucaoEfetivaIsNotNull();

    @Query("SELECT l FROM Locacao l WHERE l.cliente.id = :clienteId " +
            "AND l.dtDevolucaoEfetiva IS NULL AND l.dtDevolucaoPrevista > CURRENT_DATE")
    List<Locacao> findLocacoesEmAbertoByClienteId(Long clienteId);
}
