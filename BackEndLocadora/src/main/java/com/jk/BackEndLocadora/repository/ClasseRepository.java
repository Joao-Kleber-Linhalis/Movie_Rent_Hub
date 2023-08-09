package com.jk.BackEndLocadora.repository;

import com.jk.BackEndLocadora.domain.Ator;
import com.jk.BackEndLocadora.domain.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClasseRepository extends JpaRepository<Classe,Long> {
}
