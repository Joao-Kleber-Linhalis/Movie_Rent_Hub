package com.jk.BackEndLocadora.repository;

import com.jk.BackEndLocadora.domain.Ator;
import com.jk.BackEndLocadora.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}
