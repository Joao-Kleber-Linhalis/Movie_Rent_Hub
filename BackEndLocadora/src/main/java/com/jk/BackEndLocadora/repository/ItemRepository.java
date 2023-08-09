package com.jk.BackEndLocadora.repository;

import com.jk.BackEndLocadora.domain.Diretor;
import com.jk.BackEndLocadora.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
}
