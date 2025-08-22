package com.example.department_service.api.controllers;

import com.example.department_service.api.responses.ApiResponse;
import com.example.department_service.services.employee_service.EmployeeService;
import com.example.department_service.services.employee_service.dto.EmployeeDetail;
import com.example.department_service.services.employee_service.dto.SaveEmployee;
import com.example.department_service.services.exceptions.NotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> search(
        @RequestParam(defaultValue = "") String searchTerm,
        @RequestParam(defaultValue = "") Long deptId,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer pageSize

    ) {
        var employees = employeeService.searchEmployees(deptId, searchTerm, page, pageSize);
        var count = employeeService.countEmployees(deptId, searchTerm);
        var totalPages = (count + pageSize - 1) / pageSize;

        for (var emp : employees) {
            emp.getDepartment();
        }

        return ApiResponse.ok(Map.of(
            "employees", employees,
            "count", count,
            "totalPages", totalPages,
            "pageSize", pageSize,
            "page", page
        ));
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
        var cat = employeeService.findById(id);
        if (cat == null) {
            throw new NotFoundException("Employee ID", id.toString());
        }
        return ApiResponse.ok(EmployeeDetail.fromEntity(cat));
    }



    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SaveEmployee body) throws Exception {
        return ApiResponse.created(
            EmployeeDetail.fromEntity(employeeService.save(null, body))
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(
        @PathVariable Long id,
        @Valid @RequestBody SaveEmployee body
    ) throws Exception {
        return ApiResponse.ok(
            EmployeeDetail.fromEntity(employeeService.save(id, body))
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        employeeService.deleteById(id);
        return ApiResponse.noContent();
    }
}
