package com.example.department_service.api.controllers;

import com.example.department_service.api.responses.ApiResponse;
import com.example.department_service.services.department_service.DepartmentService;
import com.example.department_service.services.department_service.dto.department.DepartmentDetail;
import com.example.department_service.services.department_service.dto.department.SaveDepartment;
import com.example.department_service.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        return ApiResponse.ok(departmentService
            .getAll().stream()
            .map(DepartmentDetail::fromEntity)
            .toList()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
        var cat = departmentService.findById(id);
        if (cat == null) {
            throw new NotFoundException("Department ID", id.toString());
        }
        return ApiResponse.ok(DepartmentDetail.fromEntity(cat));
    }

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody SaveDepartment body) throws Exception {
        return ApiResponse.created(
            DepartmentDetail.fromEntity(departmentService.save(null, body))
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(
        @PathVariable Long id,
        @Validated @RequestBody SaveDepartment body
    ) throws Exception {
        return ApiResponse.ok(
            DepartmentDetail.fromEntity(departmentService.save(id, body))
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        departmentService.deleteById(id);
        return ApiResponse.noContent();
    }
}
