package com.example.department_service.services.employee_service.drivers;

import com.example.department_service.models.Employee;
import com.example.department_service.repositories.EmployeeRepository;
import com.example.department_service.services.department_service.DepartmentService;
import com.example.department_service.services.employee_service.dto.SaveEmployee;
import com.example.department_service.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements com.example.department_service.services.employee_service.EmployeeService {

    @Autowired
    private DepartmentService departmentService;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> findByFullName(String keyword) {
        return employeeRepository.findByFullNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<Employee> findByDepartmentId(Long deptId) {
        return employeeRepository.findByDepartmentId(deptId);
    }

    @Override
    public List<Employee> searchEmployees(Long deptId, String name, Integer page, Integer size) {
        var pagination = PageRequest.of(page, size);
        return employeeRepository.searchEmployees(deptId, name, pagination).getContent();
    }

    @Override
    public Long countEmployees(Long deptId, String name) {
        return employeeRepository.countEmployees(deptId, name);
    }

    @Override
    public boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }

    @Override
    public Employee save(Long id, SaveEmployee data) throws Exception {
        var employee = new Employee();
        if (id != null) {
            employee = findById(id);
            if (employee == null) {
                throw new NotFoundException("Employee ID", id.toString());
            }
        }

        var department = departmentService.findById(data.getDepartmentId());
        if (department == null) {
            throw new NotFoundException("Department ID", data.getDepartmentId().toString());
        }

        employee.setFullName(data.getFullName());
        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        if (! existsById(id)) {
            throw new NotFoundException("Employee ID", id.toString());
        }
        employeeRepository.deleteById(id);
    }
}
