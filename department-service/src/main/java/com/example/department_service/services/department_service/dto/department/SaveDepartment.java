package com.example.department_service.services.department_service.dto.department;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveDepartment {

    @NotEmpty(message = "Department name is required")
    @Length(min = 3, max = 100, message = "Department name must be within 3 - 100 characters.")
    private String name;
}
