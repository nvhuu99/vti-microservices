package com.example.department_service.repositories;

import com.example.department_service.models.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findAll();
    Page<Department> findAll(Pageable pageable);
    long count();
}
