package com.example.department_service.services.department_service.dto.department;

import com.example.department_service.models.Department;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentDetail {

    private Long id;

    private  String name;

    public static DepartmentDetail fromEntity(Department entity) {
        return new DepartmentDetail(
            entity.getId(),
            entity.getName()
        );
    }
}
