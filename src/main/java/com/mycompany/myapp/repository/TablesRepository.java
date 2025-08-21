package com.mycompany.myapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.mycompany.myapp.domain.Tables;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface TablesRepository extends JpaRepository<Tables, String> , JpaSpecificationExecutor<Tables> {
    
    @Query("SELECT t FROM Tables t WHERE t.isDeleted = false")
    List<Tables> findAllByIsDeletedFalse();

    Optional<Tables> findByIdAndIsDeletedFalse(String id);
    
}
