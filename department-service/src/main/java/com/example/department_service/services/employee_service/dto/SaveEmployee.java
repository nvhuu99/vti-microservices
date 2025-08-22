package com.example.department_service.services.employee_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveEmployee {

    @NotEmpty(message = "Full name is required")
    @Length(min = 2, max = 100, message = "Employee full name must be within 2 - 100 characters")
    private String fullName;

    @NotEmpty(message = "DepartmentID is required")
    private Long departmentId;
}
