package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String>, JpaSpecificationExecutor<Category> { // THÊM JpaSpecificationExecutor
    @Query("SELECT c FROM Category c WHERE c.isDeleted = false")
    List<Category> findAllByIsDeletedFalse();
    Optional<Category> findByIdAndIsDeletedFalse(String id);
}