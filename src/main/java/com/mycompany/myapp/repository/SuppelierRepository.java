package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Suppelier;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface SuppelierRepository extends JpaRepository<Suppelier, String> {
    @Query("SELECT s FROM  Suppelier as s where s.userId = :userId AND s.isDeleted = false ")
    List<Suppelier> findAlLByUserId(Long userId);

    @Query("SELECT s FROM Suppelier s WHERE s.isDeleted = false")
    List<Suppelier> findAllByIsDeletedFalse();

    Optional<Suppelier> findByIdAndIsDeletedFalse(String id);
}
