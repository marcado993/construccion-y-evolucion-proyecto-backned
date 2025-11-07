package com.example.demo.repository;

import com.example.demo.model.Senaletica;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para gestionar señaléticas
 */
@Repository
public interface SenaleticaRepository extends JpaRepository<Senaletica, Long> {
    
    /**
     * Encuentra todas las señaléticas de un usuario ordenadas por fecha de creación descendente
     */
    List<Senaletica> findByUserOrderByFechaCreacionDesc(User user);
    
    /**
     * Encuentra las últimas N señaléticas de un usuario
     */
    List<Senaletica> findTop10ByUserOrderByFechaCreacionDesc(User user);
    
    /**
     * Encuentra señaléticas por tipo
     */
    List<Senaletica> findByTipoSenaletica(String tipoSenaletica);
    
    /**
     * Encuentra señaléticas más descargadas
     */
    List<Senaletica> findTop10ByOrderByDescargasDesc();
}
