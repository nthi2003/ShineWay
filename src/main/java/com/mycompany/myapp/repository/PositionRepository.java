package com.mycompany.myapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, String> {
    @Query("SELECT c FROM Position c WHERE c.isDeleted = false")
    List<Position> findAllByIsDeletedFalse();

    Optional<Position> findByIdAndIsDeletedFalse(String id);
}
