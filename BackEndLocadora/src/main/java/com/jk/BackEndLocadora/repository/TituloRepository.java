package com.jk.BackEndLocadora.repository;

import com.jk.BackEndLocadora.domain.Locacao;
import com.jk.BackEndLocadora.domain.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TituloRepository extends JpaRepository<Titulo,Long> {
}
