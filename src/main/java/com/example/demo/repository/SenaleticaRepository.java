package com.example.demo.repository;

import com.example.demo.model.Senaletica;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Senaletica entity operations.
 */
@Repository
public interface SenaleticaRepository extends JpaRepository<Senaletica, Long> {
    
    /**
     * Finds all senaleticas by user ordered by creation date descending.
     *
     * @param user the user
     * @return list of senaleticas
     */
    List<Senaletica> findByUserOrderByFechaCreacionDesc(User user);
    
    /**
     * Finds top 10 senaleticas by user ordered by creation date descending.
     *
     * @param user the user
     * @return list of senaleticas
     */
    List<Senaletica> findTop10ByUserOrderByFechaCreacionDesc(User user);
    
    /**
     * Finds senaleticas by type.
     *
     * @param tipoSenaletica the senaletica type
     * @return list of senaleticas
     */
    List<Senaletica> findByTipoSenaletica(String tipoSenaletica);
    
    /**
     * Finds top 10 most downloaded senaleticas.
     *
     * @return list of senaleticas
     */
    List<Senaletica> findTop10ByOrderByDescargasDesc();
}
