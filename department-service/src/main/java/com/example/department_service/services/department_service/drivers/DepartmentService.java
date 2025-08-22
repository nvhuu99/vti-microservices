package com.example.department_service.services.department_service.drivers;

import com.example.department_service.models.Department;
import com.example.department_service.repositories.DepartmentRepository;
import com.example.department_service.services.department_service.dto.department.SaveDepartment;
import com.example.department_service.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService implements com.example.department_service.services.department_service.DepartmentService {

    @Autowired
    private DepartmentRepository repo;

    @Override
    public List<Department> getAll() { return repo.findAll(); }

    @Override
    public List<Department> search(Integer page, Integer size) {
        var result = repo.findAll(PageRequest.of(page, size));
        return result.getContent();
    }

    @Override
    public Long countAll() { return repo.count(); }

    @Override
    public Department findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }

    @Override
    public Department save(Long id, SaveDepartment data) throws Exception {
        Department category = new Department();
        if (id != null) {
            category = findById(id);
            if (category == null) {
                throw new NotFoundException("Department ID", id.toString());
            }
        }
        category.setName(data.getName());
        return repo.save(category);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        if (! existsById(id)) {
            throw new NotFoundException("Department ID", id.toString());
        }
        repo.deleteById(id);
    }
}
