package com.example.department_service.services.employee_service.dto;


import com.example.department_service.models.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDetail {
    private Long id;

    private String fullName;

    private Long categoryId;

    private String categoryName;

    public static EmployeeDetail fromEntity(Employee entity) {
        return new EmployeeDetail(
            entity.getId(),
            entity.getFullName(),
            entity.getDepartment().getId(),
            entity.getDepartment().getName()
        );
    }
}
