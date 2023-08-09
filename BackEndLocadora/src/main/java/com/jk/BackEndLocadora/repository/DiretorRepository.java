package com.jk.BackEndLocadora.repository;

import com.jk.BackEndLocadora.domain.Dependente;
import com.jk.BackEndLocadora.domain.Diretor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiretorRepository extends JpaRepository<Diretor,Long> {
}
