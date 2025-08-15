package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
 @Query("SELECT c FROM Category c WHERE c.isdeleted = false")
 List<Category> findAllByIsdeletedFalse();
 Optional<Category> findByIdAndIsdeletedFalse(String id);
}
