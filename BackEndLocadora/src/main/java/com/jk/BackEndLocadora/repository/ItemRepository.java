package com.jk.BackEndLocadora.repository;

import com.jk.BackEndLocadora.domain.Ator;
import com.jk.BackEndLocadora.domain.Diretor;
import com.jk.BackEndLocadora.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {

    Optional<Item> findBynumSerieAndAtivo(Long numSerie, boolean ativo);

    Optional<Item> findByIdAndAtivo(Long id, Boolean status);

    @Query("SELECT t FROM Item t WHERE t.ativo = :status ORDER BY t.id")
    List<Item> findByStatus(boolean status);
}
