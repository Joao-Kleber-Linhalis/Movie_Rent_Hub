package com.jk.BackEndLocadora.repository;

import com.jk.BackEndLocadora.domain.Ator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtorRepository extends JpaRepository<Ator,Long> {
}
