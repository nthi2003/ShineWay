package com.mycompany.myapp.repository;

import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.Attendance;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, String>, JpaSpecificationExecutor<Attendance> {

    @Query("SELECT a FROM Attendance a WHERE a.isDeleted = false")
     List<Attendance> findAllByIsDeletedFalse();
    Optional<Attendance> findByIdAndIsDeletedFalse(String id);
    // Define methods for Attendance entity operations here
    // For example:
    // List<Attendance> findByUserId(String userId);
    // Optional<Attendance> findByIdAndIsDeletedFalse(String id);
    
    // Note: The actual implementation of methods will depend on the Attendance entity structure
    
}
