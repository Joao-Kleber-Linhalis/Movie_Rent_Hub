package com.jk.BackEndLocadora.repository;

import com.jk.BackEndLocadora.domain.Ator;
import com.jk.BackEndLocadora.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    Optional<Cliente> findByIdAndAtivo(Long id, boolean ativo);

    Optional<Cliente> findByCpf(String cpf);

    @Query("SELECT t FROM Cliente t WHERE t.ativo = :status ORDER BY t.id")
    List<Cliente> findByStatus(boolean status);

    @Query("SELECT d.nome FROM Dependente d WHERE d.cliente.id = :clienteId AND d.ativo = true")
    List<String> findActiveDependentesByClienteId(Long clienteId);
}
