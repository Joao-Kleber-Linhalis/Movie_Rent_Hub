package com.jk.BackEndLocadora.repository;

import com.jk.BackEndLocadora.domain.Item;
import com.jk.BackEndLocadora.domain.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocacaoRepository extends JpaRepository<Locacao,Long> {


}
