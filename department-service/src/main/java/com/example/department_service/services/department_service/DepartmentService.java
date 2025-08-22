package com.example.department_service.services.department_service;

import com.example.department_service.models.Department;
import com.example.department_service.services.department_service.dto.department.SaveDepartment;

import java.util.List;

public interface DepartmentService {
    List<Department> getAll();
    List<Department> search(Integer page, Integer size);
    Long countAll();
    Department findById(Long id);
    boolean existsById(Long id);
    Department save(Long id, SaveDepartment data) throws Exception;
    void deleteById(Long id) throws Exception;
}
