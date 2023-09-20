package com.jk.BackEndLocadora.repository;

import com.jk.BackEndLocadora.domain.Ator;
import com.jk.BackEndLocadora.domain.Cliente;
import com.jk.BackEndLocadora.domain.Dependente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DependenteRepository extends JpaRepository<Dependente,Long> {

    Optional<Dependente> findByIdAndAtivo(Long id, boolean ativo);

    @Query("SELECT t FROM Dependente t WHERE t.ativo = :status ORDER BY t.id")
    List<Dependente> findByStatus(boolean status);
}
