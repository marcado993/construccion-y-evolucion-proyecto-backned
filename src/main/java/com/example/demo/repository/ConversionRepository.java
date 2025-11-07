package com.example.demo.repository;

import com.example.demo.model.Conversion;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversionRepository extends JpaRepository<Conversion, Long> {
    
    // Obtener conversiones de un usuario específico
    List<Conversion> findByUser(User user);
    
    // Obtener conversiones de un usuario ordenadas por fecha
    List<Conversion> findByUserOrderByFechaDesc(User user);
    
    // Obtener conversiones por tipo
    List<Conversion> findByTipo(String tipo);
    
    // Obtener últimas N conversiones de un usuario
    List<Conversion> findTop10ByUserOrderByFechaDesc(User user);
    
    // Obtener conversiones de un usuario filtradas por tipo
    List<Conversion> findByUserAndTipoOrderByFechaDesc(User user, String tipo);
    
    // Eliminar todas las conversiones de un usuario
    void deleteByUser(User user);
    
    // Contar conversiones por usuario
    long countByUser(User user);
    
    // Contar conversiones por usuario y tipo
    long countByUserAndTipo(User user, String tipo);
    
    // Obtener estadísticas de caracteres convertidos
    @Query("SELECT SUM(c.longitudOriginal) FROM Conversion c WHERE c.user = :user")
    Long sumLongitudOriginalByUser(@Param("user") User user);
}

