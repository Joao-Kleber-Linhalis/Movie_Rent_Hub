package com.jk.BackEndLocadora.repository;

import com.jk.BackEndLocadora.domain.Cliente;
import com.jk.BackEndLocadora.domain.Dependente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DependenteRepository extends JpaRepository<Dependente,Long> {
}
