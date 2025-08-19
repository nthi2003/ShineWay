package com.mycompany.myapp.repository;
import org.springframework.stereotype.Repository;
import com.mycompany.myapp.domain.Permission;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
        Optional<Permission> findByCode(String code);

        @Query("SELECT c FROM Permission c WHERE c.isDeleted = false")
        List<Permission> findAllByIsDeletedFalse();

        Optional<Permission> findByIdAndIsDeletedFalse(String id);
}
