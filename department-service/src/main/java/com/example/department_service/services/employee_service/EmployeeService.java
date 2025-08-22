package com.example.department_service.services.employee_service;

import com.example.department_service.models.Employee;
import com.example.department_service.services.employee_service.dto.SaveEmployee;

import java.util.List;

public interface EmployeeService {

    Employee findById(Long id);

    List<Employee> findByFullName(String name);

    List<Employee> findByDepartmentId(Long deptId);

    List<Employee> getAll();

    List<Employee> searchEmployees(Long deptId, String name, Integer page, Integer size);

    Long countEmployees(Long deptId, String name);

    boolean existsById(Long id);

    Employee save(Long id, SaveEmployee course) throws Exception;

    void deleteById(Long id) throws Exception;
}
