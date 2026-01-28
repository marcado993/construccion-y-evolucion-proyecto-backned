package com.example.demo.repository;

import com.example.demo.model.Conversion;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Conversion entity operations.
 */
@Repository
public interface ConversionRepository extends JpaRepository<Conversion, Long> {
    
    /**
     * Finds conversions by user.
     *
     * @param user the user
     * @return list of conversions
     */
    List<Conversion> findByUser(User user);
    
    /**
     * Finds conversions by user ordered by date descending.
     *
     * @param user the user
     * @return list of conversions
     */
    List<Conversion> findByUserOrderByFechaDesc(User user);
    
    /**
     * Finds conversions by type.
     *
     * @param tipo the conversion type
     * @return list of conversions
     */
    List<Conversion> findByTipo(String tipo);
    
    /**
     * Finds top 10 conversions by user ordered by date descending.
     *
     * @param user the user
     * @return list of conversions
     */
    List<Conversion> findTop10ByUserOrderByFechaDesc(User user);
    
    /**
     * Finds conversions by user and type ordered by date descending.
     *
     * @param user the user
     * @param tipo the conversion type
     * @return list of conversions
     */
    List<Conversion> findByUserAndTipoOrderByFechaDesc(User user, String tipo);
    
    /**
     * Deletes conversions by user.
     *
     * @param user the user
     */
    void deleteByUser(User user);
    
    /**
     * Counts conversions by user.
     *
     * @param user the user
     * @return the count
     */
    long countByUser(User user);
    
    /**
     * Counts conversions by user and type.
     *
     * @param user the user
     * @param tipo the conversion type
     * @return the count
     */
    long countByUserAndTipo(User user, String tipo);
    
    /**
     * Sums original text length by user.
     *
     * @param user the user
     * @return the sum
     */
    @Query("SELECT SUM(c.longitudOriginal) FROM Conversion c WHERE c.user = :user")
    Long sumLongitudOriginalByUser(@Param("user") User user);
}

