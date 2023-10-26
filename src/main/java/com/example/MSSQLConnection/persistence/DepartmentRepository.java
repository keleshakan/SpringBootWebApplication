package com.example.MSSQLConnection.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "select * from department with (xlock, rowlock) where id = ?1", nativeQuery = true)
    Optional<Department> findById(Long id);
}
