package com.example.department_service.repositories;

import com.example.department_service.models.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByFullNameContainingIgnoreCase(String name);

    List<Employee> findByDepartmentId(Long deptId);

    @Query("""
        SELECT e FROM Employee e
        WHERE (:departmentId IS NULL OR e.department.id = :departmentId)
          AND (:name IS NULL OR LOWER(e.full_name) LIKE LOWER(CONCAT('%', :name, '%')))
    """)
    Page<Employee> searchEmployees(
        @Param("departmentId") Long departmentId,
        @Param("name") String name,
        Pageable pageable
    );

    @Query("""
        SELECT COUNT(e) FROM Employee e
        WHERE (:departmentId IS NULL OR e.department.id = :departmentId)
          AND (:name IS NULL OR LOWER(c.full_name) LIKE LOWER(CONCAT('%', :name, '%')))
    """)
    long countEmployees(
        @Param("departmentId") Long departmentId,
        @Param("name") String name
    );
}
